#!/usr/bin/env python3
"""Remove only top-level malformed @Named annotations from a derived reading JAR.

Classfile Code attributes, constant pools, members and all other attributes stay byte-identical.
This changes annotation reflection and is never a replacement runtime client.
"""
import hashlib
import json
from pathlib import Path
import struct
import sys
import tempfile
import os
import zipfile

ROOT = Path(__file__).resolve().parents[2]
sys.path.insert(0, str(ROOT / 'tools/client-240'))
from toolchain import output_path

NAMED = {b'Ljavax/inject/Named;', b'Ljakarta/inject/Named;'}
ANNOTATIONS = {b'RuntimeVisibleAnnotations', b'RuntimeInvisibleAnnotations'}


def sha(data):
    return hashlib.sha256(data).hexdigest()


class Reader:
    def __init__(self, data):
        self.data, self.at = data, 0

    def take(self, size):
        if size < 0 or self.at + size > len(self.data):
            raise ValueError('truncated classfile')
        start = self.at
        self.at += size
        return self.data[start:self.at]

    def u1(self):
        return self.take(1)[0]

    def u2(self):
        return int.from_bytes(self.take(2), 'big')

    def u4(self):
        return int.from_bytes(self.take(4), 'big')

    def done(self):
        if self.at != len(self.data):
            raise ValueError('unconsumed classfile data')


def element(reader, depth):
    if depth > 128:
        raise ValueError('annotation nesting limit')
    tag = reader.take(1)
    if tag in [b'B', b'C', b'D', b'F', b'I', b'J', b'S', b'Z', b's', b'c']:
        reader.take(2)
    elif tag == b'e':
        reader.take(4)
    elif tag == b'@':
        annotation(reader, depth + 1)
    elif tag == b'[':
        for _ in range(reader.u2()):
            element(reader, depth + 1)
    else:
        raise ValueError(f'unsupported annotation element {tag!r}')


def annotation(reader, depth=0):
    type_index = reader.u2()
    for _ in range(reader.u2()):
        reader.take(2)
        element(reader, depth + 1)
    return type_index


def class_view(data, *, clean=True):
    reader = Reader(data)
    if reader.take(4) != b'\xca\xfe\xba\xbe':
        raise ValueError('not a JVM classfile')
    reader.take(4)
    count = reader.u2()
    utf8 = {}
    index = 1
    while index < count:
        tag = reader.u1()
        if tag == 1:
            utf8[index] = reader.take(reader.u2())
        elif tag in [3, 4, 9, 10, 11, 12, 17, 18]:
            reader.take(4)
        elif tag in [5, 6]:
            reader.take(8)
            index += 1
        elif tag in [7, 8, 16, 19, 20]:
            reader.take(2)
        elif tag == 15:
            reader.take(3)
        else:
            raise ValueError(f'unknown constant pool tag {tag}')
        index += 1
    prefix = data[:reader.at]
    removed, code, members, protected = [], {}, [], []

    def attributes(owner):
        result = []
        for _ in range(reader.u2()):
            start = reader.at
            name_index = reader.u2()
            payload = reader.take(reader.u4())
            name = utf8[name_index]
            if name == b'Code':
                code[owner] = sha(payload)
            if name in ANNOTATIONS:
                annotations = Reader(payload)
                kept = []
                for _ in range(annotations.u2()):
                    begin = annotations.at
                    type_index = annotation(annotations)
                    raw = payload[begin:annotations.at]
                    if utf8[type_index] in NAMED:
                        removed.append({'owner': owner, 'attribute': name.decode(), 'annotation_sha256': sha(raw)})
                    else:
                        kept.append(raw)
                annotations.done()
                # Retain even an empty attribute, altering only its count and removed entries.
                updated = struct.pack('>H', len(kept)) + b''.join(kept)
                protected.append((owner, name.hex(), sha(updated)))
                result.append(struct.pack('>HI', name_index, len(updated)) + updated if clean else data[start:reader.at])
            else:
                protected.append((owner, name.hex(), sha(payload)))
                result.append(data[start:reader.at])
        return struct.pack('>H', len(result)) + b''.join(result)

    header_at = reader.at
    reader.take(6)  # access, this, super
    reader.take(reader.u2() * 2)
    header = data[header_at:reader.at]
    sections = []
    for kind in ['field', 'method']:
        size = reader.u2()
        section = [struct.pack('>H', size)]
        for _ in range(size):
            start = reader.at
            access, name, descriptor = reader.u2(), reader.u2(), reader.u2()
            key = kind + ':' + utf8[name].decode('utf-8', 'replace') + utf8[descriptor].decode('utf-8', 'replace')
            members.append((kind, access, utf8[name].hex(), utf8[descriptor].hex()))
            section.extend([data[start:reader.at], attributes(key)])
        sections.append(b''.join(section))
    end = attributes('class')
    reader.done()
    result = prefix + header + b''.join(sections) + end
    return result, {'constant_pool_sha256': sha(prefix), 'header_sha256': sha(header),
                    'members': members, 'code_attributes': code, 'protected_attributes': protected}, removed


def build_metadata_view(original, destination):
    destination = output_path(destination)
    destination.parent.mkdir(parents=True, exist_ok=True)
    report_path = destination.with_suffix('.json')
    source_hash = sha(original.read_bytes())
    generator_hash = sha(Path(__file__).read_bytes())
    if destination.is_file() and report_path.is_file():
        previous = json.loads(report_path.read_text())
        if (previous.get('input_sha256') == source_hash and previous.get('generator_sha256') == generator_hash
                and previous.get('output_sha256') == sha(destination.read_bytes())):
            return previous
    classes, resources = {}, 0
    handle, scratch_name = tempfile.mkstemp(prefix='metadata-', suffix='.jar', dir=destination.parent)
    os.close(handle)
    scratch = Path(scratch_name)
    try:
        report = write_view(original, scratch, source_hash, generator_hash)
        scratch.replace(destination)
        temporary_report = scratch.with_suffix('.json')
        temporary_report.write_text(json.dumps(report, indent=2) + '\n')
        temporary_report.replace(report_path)
        return report
    finally:
        scratch.unlink(missing_ok=True)


def write_view(original, destination, source_hash, generator_hash):
    classes, resources = {}, 0
    with zipfile.ZipFile(original) as source, zipfile.ZipFile(destination, 'w', compression=zipfile.ZIP_DEFLATED) as target:
        names = source.namelist()
        if len(names) != len(set(names)):
            raise ValueError('duplicate ZIP entries')
        for entry in source.infolist():
            data = source.read(entry)
            if entry.filename.endswith('.class'):
                updated, before, removed = class_view(data)
                _, after, _ = class_view(updated, clean=False)
                if before != after:
                    raise ValueError(f'class structure or Code changed: {entry.filename}')
                if class_view(updated)[0] != updated:
                    raise ValueError('metadata cleanup is not idempotent')
                classes[entry.filename] = {'input_sha256': sha(data), 'output_sha256': sha(updated),
                    'code_attributes': before['code_attributes'], 'removed': removed}
                target.writestr(entry, updated)
            else:
                target.writestr(entry, data)
                resources += 1
    report = {'schema': 1, 'view': 'metadata-clean', 'input_sha256': source_hash,
        'output_sha256': sha(destination.read_bytes()), 'generator_sha256': generator_hash,
        'classes': classes, 'resources_preserved': resources,
        'removed_annotations': sum(len(cls['removed']) for cls in classes.values()),
        'method_code_attributes_preserved': sum(len(cls['code_attributes']) for cls in classes.values()),
        'proof': 'all Code attributes (including exception/debug/frame tables), constant pools, headers, member identities, other attributes and non-Named annotations byte-identical',
        'limitation': 'annotation reflection differs; analysis-only, do not execute or log in with this derived JAR'}
    return report


if __name__ == '__main__':
    original = ROOT / 'references/gamepacks/osrs-240/injected-client-1.12.39.jar'
    out = ROOT / '.cache/client-240/recaf/views/metadata-clean.jar'
    report = build_metadata_view(original, out)
    print(json.dumps({key: value for key, value in report.items() if key != 'classes'}, indent=2))
