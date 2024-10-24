# RTE Yocto - Development process description

## Intro

We are using the [Yocto Project](https://www.yoctoproject.org/) as a build
system.

We are using set of the publicly available layers, but also our custom layer
`meta-rte`, which is [described there](../layer-description.md).

The most important repository, which defines a release of the `RTE Yocto BSP`
is the repository where you currently are - [meta-rte](https://gitlab.com/3mdeb/rte/meta-rte)
repository. Tags in this repository correspond to the releases of the whole
`RTE Yocto BSP`. The content (meta layers and their revisions) of given
`RTE Yocto BSP` release is defined by the
[kas file](https://gitlab.com/3mdeb/rte/meta-rte/blob/master/kas.yml).
Description of kas file add project configuration can be found
[here](https://kas.readthedocs.io/en/0.19.0/userguide.html#project-configuration).

## Prerequisites

Prerequisites for the host PC to run the `RTE Yocto BSP`.

### Hardware requirements

Minimal recommended hardware is:
- 4 core CPU,
- 8GB memory,
- 100G storage.

### Software requirements

- Linux distribution with [Docker](https://docs.docker.com/install/) installed
- [kas](https://github.com/siemens/kas) installed. The preferred way is to use
it inside container, via the
[kas-docker script](https://github.com/siemens/kas/blob/master/kas-docker).
Check [meta-rte prerequisites](https://gitlab.com/3mdeb/rte/meta-rte#prerequisites)
for more details.

## Setting up the RTE Yocto BSP

Given release of the `RTE Yocto BSP` can be set up by checkout to proper tag
in `meta-rte` repository.

### Building images

```sh
mkdir rte-sdk
cd rte-sdk
git clone git@gitlab.com:3mdeb/rte/meta-rte.git
kas-docker --ssh-dir ~/ssh-keys build meta-rte/kas.yml
```

It will clone layers defined in `kas file`, set up everything and build
recipes defined in `target` section in kas file. After the `RTE Yocto BSP` has
been set up, the `rte-sdk` content can look like:

```sh
rte-sdk
├── build
├── meta-openembedded
├── meta-rte
├── meta-sunxi
├── meta-swupdate
└── poky

```

`build` directory contains a set of sub-directories with configuration data
(`conf` directory) and build results (`tmp` directory). Output files (e.g.
built images) can be found in `build/tmp/deploy/images/orange-pi-zero`.

### Building recipe

[A recipe](https://www.yoctoproject.org/docs/latest/mega-manual/mega-manual.html#some-basic-terms)
is a set of metadata to build given component. A recipe can build be used to
build a small package, Linux kernel, or even the whole image.

Alternatively, you can invoke usual bitbake steps manually, e.g.:

```sh
SHELL=bash kas-docker --ssh-dir ~/.ssh/ssh-keys shell meta-rte/kas.yml -c 'bitbake core-image-minimal-swu'
```

When bash is your default shell, you don't have to set `SHELL=bash` like it was
shown above.

## Next steps

Once you are familiar with the `RTE Yocto - Development process description`,
you can continue with the:

- [release process](release.md)
