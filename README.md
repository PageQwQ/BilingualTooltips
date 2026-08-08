<div align="center"><center>

# Bilingual Tooltips

The mod requires [Cloth Config API](https://modrinth.com/mod/cloth-config).

A simple mod that two languages ​​can be displayed simultaneously in the Tooltip.

![Preview](/Images/preview.png)

</center></div>

This mod provides a configuration interface that allows you to freely set a secondary language.

You can also configure the **Show mode** (Always/While holding Shift) and **position** (Below the item name/End of the tooltip) for the second language.

## Features

- Shows the item's name in a **second language** in its tooltip (e.g. Chinese and English).
- By default the secondary language is picked automatically: English clients show Chinese, Chinese clients show English. Any language code can be set manually (e.g. `en_us`, `zh_cn`, `ja_jp`).
- Config screen (via [Cloth Config](https://modrinth.com/mod/cloth-config)): enable/disable, secondary language, show mode, position, and "only when different".

## Supported versions

| Loader | Minecraft |
|--------|-----------|
| Fabric | 1.21.1 – 1.21.11, 26.1, 26.1.1, 26.1.2, 26.2 |
| NeoForge | 1.21.1, 26.1.2, 26.2 |

## Repository layout

This repository uses one branch per Minecraft version line, sharing the core code in `main`:

- `main` — shared core: `common/` (pure-Java logic: config, translation table, policies), `screen/` (Cloth config screen), `resources/` (lang files), this README, CI workflows.
- `1.21.x` — the 1.21.x subprojects (`mc-1-21-1-fabric`, `mc-1-21-x-fabric`, `mc-1-21-1-neoforge`).
- `26.x` — the 26.x subprojects (`mc-26-1-x-fabric`, `mc-26-1-2-fabric`, `mc-26-2-fabric`, `mc-26-1-2-neoforge`, `mc-26-2-neoforge`).

Each subproject is an independent Gradle project that pulls in the shared core via relative `srcDirs`. `mc-1-21-x-fabric` and `mc-26-1-x-fabric` are parameterized: pass `-Pmc_version=X` to build a specific patch (e.g. `-Pmc_version=1.21.9`).

## Building

Requirements: JDK 21 (for 1.21.1 and NeoForge 1.21.1) and JDK 25 (for everything else). Set `JAVA_HOME` accordingly before running Gradle.

```sh
# Fabric 1.21.1
cd mc-1-21-1-fabric && JAVA_HOME=<jdk21> ./gradlew build

# Fabric 1.21.2 – 1.21.11 (pick a version)
cd mc-1-21-x-fabric && JAVA_HOME=<jdk25> ./gradlew build -Pmc_version=1.21.9

# Fabric 26.1 / 26.1.1 / 26.1.2
cd mc-26-1-x-fabric && JAVA_HOME=<jdk25> ./gradlew build -Pmc_version=26.1

# Fabric 26.2
cd mc-26-2-fabric && JAVA_HOME=<jdk25> ./gradlew build

# NeoForge 1.21.1 / 26.1.2 / 26.2
cd mc-1-21-1-neoforge  && JAVA_HOME=<jdk21> ./gradlew build
cd mc-26-1-2-neoforge  && JAVA_HOME=<jdk25> ./gradlew build
cd mc-26-2-neoforge    && JAVA_HOME=<jdk25> ./gradlew build
```

Run a development client with `./gradlew runClient` (same `JAVA_HOME` and `-Pmc_version` rules).

## Continuous Integration

`.github/workflows/build.yml` runs on every push/PR to `main`, `1.21.x` and `26.x`. It discovers all `mc-*/` subprojects on the branch, picks the required JDK per project (JDK 21 for `mc-1-21-1-*`, JDK 25 for the rest) using a matrix, runs `./gradlew build`, and uploads the produced jars as build artifacts.

## Configuration

Config file: `config/bilingualtooltips.json` (created on first run). Fields:

| Field | Type | Default | Description |
|-------|------|---------|-------------|
| `enabled` | bool | `true` | Master switch |
| `secondaryLanguage` | string | `"auto"` | Secondary language code; `"auto"` = English on Chinese clients, Chinese otherwise |
| `showMode` | `"ALWAYS"` \| `"HOLD_SHIFT"` | `"ALWAYS"` | When the second name is shown |
| `position` | `"BELOW_NAME"` \| `"END"` | `"BELOW_NAME"` | Tooltip position of the second name |
| `onlyWhenDifferent` | bool | `true` | Hide the second name when it equals the displayed name |

## License

MIT
