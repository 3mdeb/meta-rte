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

## OpenVPN

There is a possibility to use OpenVPN. The only thing you have to do is
copy the config file to the `openvpn` folder on `storage` partition of
SD card with the name `rte.conf`.

## Serial

You can access the DUT's serial connection from the RS232/UART port at
`/dev/ttyS1`, for example, through Minicom:
```sh
minicom -D /dev/ttyS1
```

# Development workflow

## Individual MR

> Our [change log](CHANGELOG.md) mostly depends on the merge events and the
> merge request names are presented as introduced changes in this file.

* Commit changes

* Push on branch and create pull request

* Wait for review and merge

# Meta-RTE Yocto BSP documentation

* [BSP layers description](docs/layer-description.md)

* [Development process description](docs/development.md)

* [Release process description](docs/release.md)

[kas-docker]: https://github.com/siemens/kas/blob/master/kas-docker
