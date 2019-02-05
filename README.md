meta-rte
========

# Usage

## Prerequisites

[kas](https://github.com/siemens/kas) installed. The preferred way is to use it
inside container, via the [kas-docker] script.

### docker installation

* [docker installation](https://docs.docker.com/install/)

### kas-docker installation

* [kas-docker] installation:

```
wget -O ~/bin/kas-docker https://raw.githubusercontent.com/siemens/kas/master/kas-docker
chmod +x ~/bin/kas-docker
```

## Build

```
mkdir rte-sdk
cd rte-sdk
git clone https://github.com/3mdeb/meta-rte.git
kas-docker build meta-rte/kas.yml
```

## Flash

The easiest way to flash image on all system platforms is to use `etcher`. It
is multi-platform application that is available for Linux, Windows or macOS.

- Download and install [etcher](https://etcher.io/).

- Insert the SD card to the SD card reader of your host PC.

- Run etcher.

- Click on `Select Image` and select built image. There is no need to unpack
the image first.

   The SD card reader should be picked automatically. If more than one readers
   are present, click on `Change` and select the one you want to use.

- When confirmed, click on the `Flash` button to start flashing procedure.

## Login to the system

```
Login: root
Password: meta-rte
```

# Development workflow

## Individual MR

> Our [change log](CHANGELOG.md) mostly depends on the merge events and the
> merge request names are presented as introduced changes in this file.

* Commit changes

* Push on branch and create merge request

* Wait for review and merge

## Release new version

* Pull the merge events into local repository

* Bump `DISTRO_VERSION` in the `conf/distro/rte.conf` file

* Create the tag

* Update change log:

> [auto-changelog](https://github.com/CookPete/auto-changelog) tool is used
> there

```.bash
generate-changelog.sh
```

* Commit and push change log

> We either can push directly to master, or via another PR. Neither solution
> seems perfect for me at the moment.

[kas-docker]: https://github.com/siemens/kas/blob/master/kas-docker
