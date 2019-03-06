meta-rte
========

# Usage

> [rte-oe-manifest](https://gitlab.com/3mdeb/rte/rte-oe-manifest) is deprecated

## Prerequisites

[kas](https://github.com/siemens/kas) installed. The preferred way is to use it
inside container, via the
[kas-docker script](https://github.com/siemens/kas/blob/master/kas-docker)

### docker installation

* [docker installation](https://docs.docker.com/install/)

### kas-docker installation

* [kas-docker] installation:

```
wget -O ~/bin/kas-docker https://raw.githubusercontent.com/siemens/kas/master/kas-docker
chmod +x ~/bin/kas-docker
```

### ssh configuration

During fetch and build phases inside container, we need the access the following
private repositories:

* [meta-rte](https://gitlab.com/3mdeb/rte/meta-rte)
* [RteCtrl](https://gitlab.com/3mdeb/rte/RteCtrl)

It is advised not to use your main SSH key for this purpose. Rather create a
new one with limited access.

* Create `~/ssh-keys` directory with following content:

```
config
gitlab_key_ro
gitlab_key_ro.pub
```

`~/ssh-keys/config` file content:

```
Host gitlab.com
    HostName       gitlab.com
    User           git
    IdentityFile   ~/.ssh/gitlab_key_ro
    StrictHostKeyChecking no
    IdentitiesOnly yes
```

## Build

```
mkdir rte-sdk
cd rte-sdk
git clone git@gitlab.com:3mdeb/rte/meta-rte.git
kas-docker --ssh-dir ~/ssh-keys build meta-rte/kas.yml
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

* Push on branch and create pull request

* Wait for review and merge

# Meta-RTE Yocto BSP documentation

* [BSP layers description](docs/layer-description.md)

* [Development process description](docs/development.md)

* [Release process description](release.md)
