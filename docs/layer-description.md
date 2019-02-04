# meta-rte - layer description

`meta-rte` is custom layer, which provide system support we need for
[3mdeb's RTE](https://shop.3mdeb.com/product/rte/).
The structure of the meta-rte layer looks like below:

```
meta-rte
├── CHANGELOG.md
├── conf
│   ├── distro
│   └── layer.conf
├── COPYING.MIT
├── generate-changelog.sh
├── kas.yml
├── README.md
├── recipes-bsp
│   └── u-boot
├── recipes-connectivity
│   └── ser2net
├── recipes-core
│   ├── base-files
│   ├── images
│   ├── init-ifupdown
│   ├── systemd
│   └── udev-rules-rte
├── recipes-coreboot
│   └── coreboot-utils
├── recipes-devtools
│   ├── android-tools
│   ├── imx-usb-loader
│   └── stlink
├── recipes-extended
│   ├── images
│   ├── packagegroups
│   └── socketcan
├── recipes-go
│   ├── 3mdeb-rtectrl
│   └── github.com-gorilla-mux
├── recipes-kernel
│   └── linux
├── recipes-support
│   └── swupdate
├── scripts
│   └── release.sh
└── wic
    └── sunxi-mmc-spl.wks
```

Most of the files in this layer are append files which provide some custom
configuration to the upstream recipes. There is no point in describing them in
this documentation. Please refer to those files directly to find out what is
being done there. Only the crucial parts of the system are going to be
introduced there.

## conf

A [conf driectory](https://gitlab.com/3mdeb/rte/meta-rte/blob/master/conf)
contains distro configuration file `rte.conf` and layer configuration file
`layer.conf`.

## recipes-bsp

### u-boot
[U-Boot bbappend file](https://gitlab.com/3mdeb/rte/meta-rte/blob/master/recipes-bsp/u-boot/u-boot_%25.bbappend)
is used to apply custom patches and two important files:

* [fw_env.config](https://gitlab.com/3mdeb/rte/meta-rte/blob/master/recipes-bsp/u-boot/u-boot/orange-pi-zero/fw_env.config),
which contains the environment location on the `MMC` block device

* [boot.cmd](https://gitlab.com/3mdeb/rte/meta-rte/blob/master/recipes-bsp/u-boot/u-boot/orange-pi-zero/boot.cmd) -
U-Boot boot script to load the Linux kernel and device tree from proper
partition and pass the bootargs to the Linux kernel.

## recipes-connectivity
### ser2net
Recipe installing custom configuration file with specific uart devices and
service for ser2net deamon.

## recipes-core

### base-files
System hostname and [/etc/fstab](https://gitlab.com/3mdeb/rte/meta-rte/blob/master/recipes-core/base-files/base-files/fstab)
file are configured there - partition for `/boot/bootpart` and `/storage` are
mounted here.

### images

Place for images bblayers. There is only bbappend for `core-image-minimal`
image.

### systemd

Some important system settings include:
* watchdog timer configuration,
* basic network configuration,
* setting the `en_US.UTF-8` as default value of locale `LANG` variable

Refer to the [systemd append file](https://gitlab.com/3mdeb/rte/meta-rte/blob/master/recipes-core/systemd/systemd_%25.bbappend)
for details.

### udev-rules-rte

Place for definition of udev rules. For now `debug_uart_converter`,
`rs_485_converter`, `can_converter` are defined.

## recipes-coreboot
### coreboot-utils

Coreboot utils installation: `cbfstool`, `ifdtool`

## recipes-devtools

* android-tools
* imx-usb-loader
* stlink

## recipes-extended
### images

Recipes for the `SWU` images, which allow to upgrade the system using the
[SWUpdate utility](https://sbabic.github.io/swupdate/swupdate.html).
The difference between the `SWU` images and the images from `recipes-core` is
that the second contain full disk image, while the `SWU` images contain only
the image of the `rootfs` partition.

Generally, each active image from the `recipes-core` should have a
corresponding `SWU` image recipe which allows for system upgrade on the device.

Each `SWU` image recipe has a directory corresponding to it with
`sw-description` file.

This directory contain the
[sw-description file](https://sbabic.github.io/swupdate/sw-description.html#introduction).
The `MACHINE` setting will decide on which `sw-description` file will be
included in the built image.

There is only `core-image-minimal-swu` for now.

### packagegroups

Collect packages in [packagegroups](https://www.yoctoproject.org/docs/latest/dev-manual/dev-manual.html#usingpoky-extend-customimage-customtasks).

### socketcan

## recipes-go

Recipes for utils developed in `Go` language.

### 3mdeb-rtectrl

Recipes for `RTE REST API` server

### gorilla-mux

Recipes for `gorilla-mux ` - powerful URL router and dispatcher for golang.


## recipes-kernel
### linux

Provides the recipe for the linux-yocto-mainline, patches and custom defconfig.

## recipes-support
### swupdate

We use SWUpdate as the utility to upgrade our system. The directory contains
build configuration and some patches:

```
swupdate
├── files
│   ├── defconfig
│   └── orange-pi-zero
│       ├── confirm-upgrade.service
│       ├── rte-upgrade
│       └── sw-description
├── swu-confirm.bb
└── swupdate_%.bbappend
```

## scripts

* release.sh

## wic

This directory contains wic WKS files which are used to create our custom image
layout.

* sunxi-mmc-spl.wks
