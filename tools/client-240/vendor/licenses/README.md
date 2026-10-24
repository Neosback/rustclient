# Client-240 tool licenses and source availability

The JARs in `tools/client-240/vendor/` are byte-for-byte copies of the official
release artifacts named in `../dependencies.json`. The manifest records each
artifact's SHA-256 and its immutable release-download URL. The license texts
below are copied from the corresponding upstream source repositories or
release metadata.

| Artifact | License text | Upstream source | Release sources |
| --- | --- | --- | --- |
| ASM 9.6 modules | [`asm-bsd-3-clause.txt`](asm-bsd-3-clause.txt) | [OW2 ASM 9.6 source](https://gitlab.ow2.org/asm/asm/-/tree/85cf1aeb0d08be8446f6efbda962817d2a9707dd) | [ASM source JAR](https://repo1.maven.org/maven2/org/ow2/asm/asm/9.6/asm-9.6-sources.jar) |
| Tiny Remapper 0.10.3 | [`tiny-remapper-lgpl-3.0.txt`](tiny-remapper-lgpl-3.0.txt) | [FabricMC Tiny Remapper source](https://github.com/FabricMC/tiny-remapper/tree/4c71cd62a92ffe600049ba6814a2178a2ed728de) | [Tiny Remapper source JAR](https://maven.fabricmc.net/net/fabricmc/tiny-remapper/0.10.3/tiny-remapper-0.10.3-sources.jar) |
| Mapping IO 0.6.0 | [`mapping-io-apache-2.0.txt`](mapping-io-apache-2.0.txt) | [FabricMC Mapping IO 0.6 source](https://github.com/FabricMC/mapping-io/tree/dcf8fbd41c6d1da9a5267108b2895bf07ca0dd6f) | [Mapping IO source JAR](https://maven.fabricmc.net/net/fabricmc/mapping-io/0.6.0/mapping-io-0.6.0-sources.jar) |
| Vineflower 1.12.0 | [`vineflower-apache-2.0.txt`](vineflower-apache-2.0.txt) | [Vineflower 1.12.0 source](https://github.com/Vineflower/vineflower/tree/b8273988af850e8cfb234ca08d129058502b032f) | [Vineflower source JAR](https://repo1.maven.org/maven2/org/vineflower/vineflower/1.12.0/vineflower-1.12.0-sources.jar) |
| CFR 0.152 | [`cfr-mit.txt`](cfr-mit.txt) | [CFR 0.152 source](https://github.com/leibnitz27/cfr/tree/68477be3ff7171ee17ddd1a26064b9b253f1604f) | [CFR source JAR](https://repo1.maven.org/maven2/org/benf/cfr/0.152/cfr-0.152-sources.jar) |

The Tiny Remapper project does not publish a version tag for this artifact;
its release JAR and source JAR are therefore pinned by the Fabric Maven
coordinates and the SHA-256 in the manifest, while the source link points to
the upstream repository revision used for the license text. No source JARs
are needed at runtime. The corresponding 0.10.3 source JAR is nevertheless
vendored under `vendor/sources/` so the repository carries the exact source
artifact for this LGPL-covered tool.

The LGPLv3 text incorporates the terms of GPLv3. The complete GPLv3 text is
included as [`gpl-3.0.txt`](gpl-3.0.txt), copied from the [Free Software
Foundation's official GPLv3 text](https://www.gnu.org/licenses/gpl-3.0.txt).
