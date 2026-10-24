#!/usr/bin/env python3
"""Install the repository's canonical skill as a discoverable Codex symlink."""
import argparse
import os
from pathlib import Path


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--dest', type=Path, help='skills directory; defaults to CODEX_HOME/skills or ~/.codex/skills')
    args = parser.parse_args()
    source = Path(__file__).resolve().parents[2] / '.claude/skills/client-240-wave'
    if not (source / 'SKILL.md').is_file():
        parser.error(f'missing canonical skill: {source}')
    directory = args.dest or Path(os.environ.get('CODEX_HOME', Path.home() / '.codex')) / 'skills'
    target = directory.expanduser().absolute() / source.name
    if target.exists() or target.is_symlink():
        if target.is_symlink() and target.resolve() == source.resolve():
            print(f'Already installed: {target}')
            return
        parser.error(f'{target} already exists; leave it intact or choose --dest')
    target.parent.mkdir(parents=True, exist_ok=True)
    target.symlink_to(source, target_is_directory=True)
    print(f'Installed: {target} -> {source}')


if __name__ == '__main__':
    main()
