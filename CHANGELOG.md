# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/).

## [0.8.0-rc1] - 2024-10-31

### Added
- recipes-devtools/dediprog-flasher: add recipe
- recipes-extended/packagegroups/packagegroup-rte.bb: added dediprog-flasher
- dediprog-flasher/0001-add-support-for-cross-compilation.patch: added
- dediprog-flasher/0002-Makefile-remove-stripping.patch: added
- recipes-devtools/dediprog-flasher: add /usr/share/DediProg to FILES
- kas.yml: added meta-arm dependancy
- conf/distro/rte.conf: added usrmerge to DISTRO_FEATURES
- recipes-bsp/u-boot/u-boot/*: added Upstream-Status to patches
- recipes-*: added missing branch parameters to SRC_URI
- minicom: add a default config to disable hardware flow control
- rte-tests: add


### Changed
- recipes-devtools/dediprog-flasher: do_install() add PREFIX=/usr
- *: updated bitbake syntax
- kas.yml: updated fetched layers revisions to scarthgap
- conf: changed layer compatibility to scarthgap
- patches/0001-do-not-remove-wic-partitions.patch: updated
- packagegroups/packagegroup-rte.bb: bmap-tools -> bmaptool
- conf/distro/rte.conf: bump used u-boot version
- u-boot/0004-sun8i-disable-Vendor-Parameter-Protection.patch: updated
- recipes-bsp/u-boot/u-boot/*: updated context lines to fix fuzz
- recipes-bsp/u-boot/u-boot/*: reordered patches
- kas.yml: ABORT -> HALT
- recipes-graphics/xorg-proto/xcb-proto: use package from upstream
- github.com-gorilla-mux/github.com-gorilla-mux_git.bb: updated rev
- recipes-go/github.com-*: inherit go -> inherit go-mod
- recipes-go/3mdeb-rtectrl/3mdeb-rtectrl_git.bb: Updated commit
- recipes-devtools/stlink/stlink_git.bb: LICENSE = BSD -> BSD-3-Clause
- images/core-image-minimal.bbappend: updated extrausers syntax
- recipes-devtools/sd-mux/sd-mux_git.bb: changed source
- imx-usb-loader/imx-usb-loader_git.bb: updated LICENSE value
- patches/0001-do-not-remove-wic-partitions.patch: mv -> cp
- recipes-kernel/linux/linux-mainline: updated patches
- recipes-devtools/dediprog-flasher: Updated based on meta-oe patch review
- distro/rte.conf: bump to 0.8
- README.md: specify which kas version to use
- *:  Add basic pre-commit configuration
- *: Fix files so that pre-commit passes
- *: Address pre-commit for non standard recipes
- core-image-minimal.bbappend: Fix comment
- Fix build errors
- u-boot: boot.cmd: adjust boot cmd
- linux: enable obsolete GPIO_SYSFS
- rte-persistent-data: dummy recipe to create /data dir
- wic/sunxi-mmc-spl.wks: bring back data partition
- bsp: flashrom: update to 1.4.0
- connectivity: gensio: update to 2.8.7
- connectivity: ser2net: update to 4.6.2
- devtools: python3-asciinema: update to 2.4.0
- conf/distro/rte.conf: Add e2fsprogs-native to WKS_FILE_DEPENDS
- recipes-core/images/core-image-minimal: Uncomment root password settings
- recipes-kernel: linux-mainline: Add CONFIG_DEBUG_FS
- recipes-core/images/core-image-minimal.bbappend: Use encrypted root password
- recipes-go/3mdeb-rtectrl/3mdeb-rtectrl/RteCtrl.cfg: Modify GPIO expander numbers
- recipes-kernel/linux/linux-mainline: Disable WiFi chip
- distro/rte.conf: bump to 0.8.0-rc1
- CHANGLELOG: Add notes for v0.8.0-rc1


### Fixed
- linux-mainline: fix spidev patch
- tree-wide: fix license warnings
- conf/distro/rte.conf: fix p4
- distro/rte.conf: fix remaining old style override syntax


### Removed
- kas.yml: removed image-mklibs & image-prelink
- u-boot/0001-nanopi_neo_air_defconfig-Enable-eMMC-support.patch: removed
- recipes-support/openvpn: deleted



## [0.7.5] - 2024-03-12

### Added
- CHANGELOG: add info about fixing golang recipes


### Changed
- recipes-kernel: linux-mainline: Fix spidev-related patches
- recipes-kernel: linux-mainline: Fix indents and commit message in patch 0008
- rte.conf: release v0.7.5
- go: update go-based recipes to use main branch


## [0.7.4] - 2024-01-05

### Changed
- recipes-kernel/linux/linux-mainline: Add 9p kernel support
- README: Move links to the top
- docs/cpu.md: Add document with cpu command use case
- docs/cpu.md: Refine document
- README: Link docs/cpu.md
- rte.conf: release v0.7.4


## [0.7.4-rc5] - 2023-10-05

### Changed
- recipes-connectivity/ser2net: Replace old conf file with a YAML file
- recipes-go: Add cpu
- recipes-bsp: Add flashrom 1.3.0 recipe
- extended: packagegroup-rte: install vim-tiny
- distro: rte.conf: bump to v0.7.4-rc5; change CODENAME to dunfell


### Removed
- extended: packagegroup-rte: remove python3-asciinema package


## [0.7.4-rc4] - 2023-09-29

### Changed
- packagegroups: install bmap-tools
- distro: rte.conf: bump to v0.7.4-rc4


## [0.7.4-rc3] - 2023-09-29

### Added
- devtools: sd-mux: add recipe to build sd-mux utility from tizen
- packagegroup: add sd-mux


### Changed
- distro: rte.conf: bump to v0.7.4-rc3


## [0.7.4-rc2] - 2023-09-29

### Changed
- 3mdeb-rtectrl: 3mdeb-rtectrl_git.bb: bump version to 0.5.3-rc2
- distro: rte.conf: bump to v0.7.4-rc2


## [0.7.4-rc1] - 2023-09-29

### Added
- 3mdeb_rtectrl: add throttled and golang-lru to recipe


### Changed
- README.md: described swupdate process
- updated README.md with additional info about flashing, specifically using bmaptool
- adressed the issues mentioned in the review
- added info about fedora and ubuntu for the bmaptool installation
- packagegroups/packagegroup-rte.bb: Add vim
- README: Changed kas-docker to kas-container
- recipes-graphics: Add directory + xorg-proto recipes
- 3mdeb-rtectrl_git.bb: use RteCtrl version 0.5.3
- distro: rte.conf: bump to v0.7.4-rc1


### Fixed
- README.md: fix whitespaces, correct update section


## [0.7.3] - 2022-07-25

### Changed
- rte.conf: release v0.7.3


## [0.7.3-rc1] - 2022-07-18

### Added
- linux: add ina2xx support


### Changed
- distro: rte.conf: bump to 0.7.3-rc1


### Fixed
- devtools: stlink: fix incorrect path to patch


## [0.7.2-rc2] - 2022-07-18

### Added
- README.md: add info about including only current version in CHANGELOG.md
- stlink: add stlink toolset


### Changed
- CHANGELOG.md: revert to previous version
- distro: conf.rte: bump to v0.7.1-rc2


## [0.7.2-rc1] - 2022-07-12

### Added
- rte_ctrl: add rte_ctrl.sh script and curl to image


### Fixed
- ISSUE_TEMPLATE: task.md: fix typo
- rte_ctrl_script: fix files loading from drive rrather than git
- 3mdeb_rtectrl_git: fixed rte_ctrl script git source paths


## [0.7.1] - 2021-08-09

### Changed
- bsp: u-boot: regenerate MAC on Serial ID change
- docs: rte-mac-setup.md update mac generation documentation
- distro: rte.conf: bump to 0.7.1


### Removed
- bsp: u-boot: remove force MAC recalculating patch


## [0.7.0] - 2021-07-29

### Added
- recipes-devtools/python: add wakeonlan recipe
- images/core-image-minimal.bbappend: add python3-wakeonlan
- images/core-image-minimal.bbappend: add python3-setuptools
- python3-wakeonlan_1.0.0.bb: add RDEPENDS python3-setuptools
- README.md: add information about serial connection and telnet
- patches: add wic partitions patch
- python3-asciinema: add recipe
- layer.conf: add dunfell compatibility
- packagegroup-rte: add python3-asciinema
- python3-asciinema_2.0.2.bb: add pkg_recources and ncurses to RDEPEND
- python3-wakeonlan_1.2.0.bb: add python pkg_resources to RDEPENDS
- rte.conf: add wic.bmap to IMAGE_FSTYPES
- u-boot_%.bbappend: add boot.cmd
- packagegroup-rte.bb: add xradio, xradio-firmware and wpa_supplicant
- rte-setup.md: add the instruction of setup the MAC address
- README.md: add reference to rte-setup.md document
- ISSUE_TEMPLATE: add basic templates


### Changed
- recipes-devtools/openocd: install necessary config files
- core-image-minimal.bbappend: rm python3-setuptools from IMAGE_INSTALL
- README: Add instructions for serial access
- openvpn: move to recipes-support
- change from /storage to /data partition name
- linux: refresh patches to 5.4
- u-boot: refresh patches to v2020.1
- kas.yml: update refspec to dunfell
- python3-wakeonlan: update recipe to 1.2.0 pre-release from 3mdeb fork
- stlink: adjust to stlink-org github org
- layer.conf: increase priority to 90
- core-image-minimal: move python3-wakeonlan to pacakgegroup-rte
- distro: rte.conf: completely remove sysvinit
- distro: rte.conf: bump to 0.7.0
- openvpn: change /storage to /data as config dir
- rte.conf: bump u-boot version to 2020.04
- recipes-bsp/u-boot/u-boot/: update patches to new version of u-boot
- u-boot_2020.04.bb: recipe for v2020.04 of u-boot bootloader
- connectivity: ser2net: update version to 4.3.3
- connectivity: gensio: new recipe for version 2.2.4
- CHANGELOG.md: update links
- bsp: u-boot; disable Vendor Parameter Protection
- bsp: u-boot: store generated mac address in an environment variable
- bsp: u-boot: force recalculating the MAC address on every boot
- rte-setup.md: update doc
- docs: rte-mac-setup: rename doc


### Fixed
- README.md: fix links
- docs/development.md: fix minor spelling and broken link


### Removed
- socketcan: remove append file
- distro: rte.conf: remove DISTRO_FEATURES_LIBC
- distro: rte.conf: remove linux version preference
- packagegroup-rte: remove stlink due to copmilation issues


## [0.6.2] - 2019-02-05

### Added
- scripts/release.sh: add "BASE_SWU_IMAGE_NAME" variable
- docs: add initial versions of development.md, layer-description.md, release.md
- README.md: add links to documentation
- README.md: add info about openvpn
- COPYING.md: add 3mdeb copyrights


### Changed
- Add swu image to automatic release
- wic/sunxi-mmc-spl.wks: install "storage" from rootfs on "storage" partition
- Install openvpn
- 3mdeb-rtectrl: use github public repo
- scripts/release.sh: do not use --ssh-dir
- release v0.6.2


### Fixed
- scripts/release.sh: fix name of swupdate image


### Removed
- README.md: remove private repos info


## [0.6.1] - 2019-01-24

### Changed
- scripts/release.sh: run kas-docker as user
- Fix issue with launching tmux
- release v0.6.1


### Fixed
- CHANGELOG.md: fix 0.6.0 changelog


### Removed
- recipes-core/init-ifupdown/init-ifupdown_%.bbappend: remove "/etc/network/interfaces" file


## [0.6.0] - 2019-01-18

### Added
- kas.yml: add kas file
- script/release.sh: add script
- u-boot: add fw_env.config file support for swupdate needs
- swupdate: add recipe for swu image build
- recipes-bsp/u-boot/u-boot/arm/boot.cmd: add initial version
- recipes-extended/images/core-image-minimal-swu/sw-description: add zImage and dtb to swu-image
- recipes-extended/images/core-image-minimal-swu/sw-description: add comma between sections
- core-image-minimal-swu.bb: add zImage to SWU
- base-files/fstab: add custom file
- swupdate: add swu-confirm.swu
- swupdate: add rte-upgrade and confirm logic
- swupdate/files/orange-pi-zero/sw-description: add version


### Changed
- recipes-bsp/u-boot: rebase dram / cpufreq patches
- README.md: update usage documentation, add kas
- README.md: update download link to the file and fix typos
- Enable swupdate
- wic/sunxi-mmc-spl.wks: increse size of /boot partition from 16M to 32M
- core-image-minimal-swu.bb: inherit swupdate class and fixes
- core-image-minima-swu/sw-description: auto set version
- core-image-minimal-swu/sw-description: change collection name to rte
- kas.yml: clone poky from 3mdeb fork
- kas.yml: update meta-swupdate to thud
- u-boot/arm/boot.cmd: boot from root partititon
- core-image-minimal-swu/sw-description: drop zImage from SWU
- base-files_%.bbapend -> base-files_%.bbappend
- wic/sunxi-mmc-spl.wks: custom partition layout
- base-files/fstab: mount p1 at /boot/bootpart
- swupdate/defconfig -> files/defconfig
- swu-confirm: do not inherit swupdate
- distro/rte.conf: forbid wic to edit fstab file
- u-boot: move file to orange-pi-zero
- rte-upgrade: check if swu_file exists
- rte-upgrade: reboot after upgrade
- generate-changelog.sh: use dockerized auto-changelog


### Fixed
- scripts/release.sh: fix kas-docker cmd
- core-image-minimal-swu/sw-description: fix zImage name
- base-files/fstab: fix storage fstype
- u-boot/fw_env.confi: fix uboot.env path
- rte-upgrade: fix errorCheck


### Removed
- sdk: remove example config files
- swupdate: remove sw-versions file support
- core-image-minimal-swu/sw-description: remove dtb installation
- u-boot/arm/boot.cmd: remove boot_part prefix of fdtfile


## [0.5.3] - 2018-11-21

### Added
- distro/rte.conf: add u-boot dependency on wic image


### Changed
- linux-mainline: enable CONFIG_MAGIC_SYSRQ
- packagegroup-rte.bb: bc, python3, openocd
- release v0.5.3


### Fixed
- fix changelog


## [0.5.2] - 2018-11-21

### Changed
- u-boot: patches to lower DRAM and CPU freq
- release v0.5.2


## [0.5.1] - 2018-10-02

### Added
- packagegroup-rte.bb: add kernel-modules to packagegroup-rte-core


### Changed
- linux-mainline/orange-pi-zero: reduce modules in kernel config
- release v0.5.1


## [0.5.0] - 2018-10-01

### Added
- conf/layer.conf: add sumo to LAYERSERIES_COMPAT
- linux-mainline: add recent defconfig based on armbian-sunxi-dev


### Changed
- linux-mainline_git.bbappend: upgrade to 4.18.10
- linux-mainline_git.bbappend: use zImage for orange-pi-zero
- use wks file instead of sunxi-sdimage bbclass
- README.md: update release process
- 0.5.0 release


### Fixed
- fix setting kernel image to zImage


### Removed
- sunxi-mmc-spl.wks: remove fixed-size from rootfs


## [0.4.3] - 2018-09-06

### Changed
- packagegroup-rte.bb: install openssh-sftp-server
- distro/rte.conf: compress images for orange-pi-zero


## [0.4.2] - 2018-09-06

### Added
- README.md: add auto-changelog tool info


### Changed
- add CHANGELOG generation guidelines
- generate-changelog.sh: do not print commits to CHANGELOG
- README.md: update development workflow
- systemd_%.bbappend: set WDT timeout to 15 seconds


## [0.4.1] - 2018-09-06

### Added
- packagegroup-rte.bb: add some general packages
- packagegroup-rte.bb: add some general packages


### Changed
- linux-mainline: bump to 4.18.6


## [0.4.0] - 2018-08-25

### Added
- recipes-devtools/imx-usb-loader_git.bb: add recipe
- packagegroups/packagegroup-rte.bb: add rte-utils
- core-image-minimal.bbappend: add packagegroup-rte-utils
- recipes-devtools/stlink: add recipe
- packagegroup-rte.bb: add stm packagegroup
- recipes-core/udev-rules-rte: add recipe
- packagegroup-rte.bb: add udev-rules-rte  to packagegroup-imx
- imx-usb-loader: add conf file for Vitro Devkit eMMC flashing
- packagegroup-rte.bb: add wget and ca-certificates to rte-utils
- packagegroup-rte.bb: add some utils packages


### Changed
- android-tools_%.bbappend: install only fastboot
- packagegroups-rte.bb: split into core, imx, coreboot
- core-image-minimal.bbappend: update packagegroup names
- RteCtrl.service: change from forking to simple
- core-image-minimal.bbappend: set root password
- core-image-minimal.bbappend: install packagegroup-rte-stm
- ser2net.cnf: use debug_uart_converter instead of ttyUSB0
- linux-mainline_git.bbappend: upgrade to 4.17.6
- linux-mainline_git.bbappend: upgrade to 4.17.10
- sdk/config: move from rte-oe-manifest repo
- simplify local.conf.example
- local.conf.example: DL and SSTATE dirs in workspace
- distro/rte.conf: bump to 0.4.0


### Removed
- core-image-full-cmdline.bbappend: remove file


## 0.3.0 - 2018-06-19

### Added
- linux-mainline_git.bbappend: add append file
- systemd: add dhcp-network configuration for eth0
- packagegroup-rte.bb: add packagegrou-core-rte
- ser2net: add configuration and service files
- core-image-minimal.bbappend: add openssh and packagegroup-core-rte
- core-image-full-cmdline.bbappend: add packagegroup-core-rte
- packagegroup-rte.bb: add bash
- README.md: add readme
- packagegroup-rte.bb: add avahi-daemon to packagegroup-core-rte
- distro/rte.conf: add distro configuration
- linux-mainline: add mcp23017 + cleanup patches
- ser2net: add config entry for ttyS1
- conf/distro: add extra space to rootfs
- recipes-extended/packagegroups: add tmux to be installed
- recipes-go/github.com-gorilla-mux: add recipe
- recipes-go/3mdeb-rtectrl: add recipe
- packagegroup-rte.bb: add 3mdeb-rtectrl to core-rte
- packagegroup-rte.bb: add can-utils to core-rte
- packagegroup-rte.bb: add i2c-tools to core-rte
- linux-mainline/can-serial.cfg: add USB_ACM module
- reciepes-kernel/linux: add patches enabling spidev
- recipes-go/3mdeb-rtectrl: add custom config and systemd service
- recipes-coreboot: add recipes for cbfstool and ifdtool
- recipes-coreboot/coreboot-utils/coreboot-utils_git.bb: add packaging


### Changed
- initial commit
- linux-mainline_git.bbappend: build from 4.16-rc3
- linux-mainline_git.bbappend: build from 4.16-rc3
- linux-mainline: patches to enable USB, SPI, UART2, I2C
- base-files: set hostname to rte
- core-image-full-cmdline.bbappend: set root password
- linux-mainline_git.bbappend: switch to stable 4.15.7
- linux-mainline: change mcp23017 reg value to 20
- 3mdeb-rtectrl_git.bb: install webserver files
- 3mdeb-rtectrl_git.bb: package RteWeb and RteCtrl.cfg
- linux-mainline: enable can serial and raw
- linux-mainline/can-serial.cfg: compile CAN as a module
- can-utils_git.bbappend: build can-utils from vitrotv fork
- update links to RTE repositories
- recipes-coreboot: create single recipe for all tools
- recipes-coreboot: cleanup of old files
- recipes-coreboot: change coreboot utils to packages and add patch for Makefiles
- recipes-coreboot/coreboot-utils/coreboot-utils_git.bb: workaround missing GNU hash
- linux-mainline: upgrade to 4.17.2
- linux-mainline: make sure to build all USB / serial drivers


### Fixed
- linux-mainline: fix patch names
- recipes-coreboot: fix git branch and license file


### Removed
- recipes-coreboot: remove obsolete git fetcher
- 0001-utils-do-not-override-compiler-variables.patch: remove patch from root dir
- coreboot-utils: remove append file
- coreboot-utils_git.bb: remove whitespaces


[unreleased]: https://github.com/3mdeb/meta-rte/compare/v0.7.5..HEAD
[0.7.5]: https://github.com/3mdeb/meta-rte/compare/v0.7.4..v0.7.5
[0.7.4]: https://github.com/3mdeb/meta-rte/compare/v0.7.4-rc5..v0.7.4
[0.7.4-rc5]: https://github.com/3mdeb/meta-rte/compare/v0.7.4-rc4..v0.7.4-rc5
[0.7.4-rc4]: https://github.com/3mdeb/meta-rte/compare/v0.7.4-rc3..v0.7.4-rc4
[0.7.4-rc3]: https://github.com/3mdeb/meta-rte/compare/v0.7.4-rc2..v0.7.4-rc3
[0.7.4-rc2]: https://github.com/3mdeb/meta-rte/compare/v0.7.4-rc1..v0.7.4-rc2
[0.7.4-rc1]: https://github.com/3mdeb/meta-rte/compare/v0.7.3..v0.7.4-rc1
[0.7.3]: https://github.com/3mdeb/meta-rte/compare/v0.7.3-rc1..v0.7.3
[0.7.3-rc1]: https://github.com/3mdeb/meta-rte/compare/v0.7.2-rc2..v0.7.3-rc1
[0.7.2-rc2]: https://github.com/3mdeb/meta-rte/compare/v0.7.2-rc1..v0.7.2-rc2
[0.7.2-rc1]: https://github.com/3mdeb/meta-rte/compare/v0.7.1..v0.7.2-rc1
[0.7.1]: https://github.com/3mdeb/meta-rte/compare/v0.7.0..v0.7.1
[0.7.0]: https://github.com/3mdeb/meta-rte/compare/v0.6.2..v0.7.0
[0.6.2]: https://github.com/3mdeb/meta-rte/compare/v0.6.1..v0.6.2
[0.6.1]: https://github.com/3mdeb/meta-rte/compare/v0.6.0..v0.6.1
[0.6.0]: https://github.com/3mdeb/meta-rte/compare/v0.5.3..v0.6.0
[0.5.3]: https://github.com/3mdeb/meta-rte/compare/v0.5.2..v0.5.3
[0.5.2]: https://github.com/3mdeb/meta-rte/compare/v0.5.1..v0.5.2
[0.5.1]: https://github.com/3mdeb/meta-rte/compare/v0.5.0..v0.5.1
[0.5.0]: https://github.com/3mdeb/meta-rte/compare/v0.4.3..v0.5.0
[0.4.3]: https://github.com/3mdeb/meta-rte/compare/v0.4.2..v0.4.3
[0.4.2]: https://github.com/3mdeb/meta-rte/compare/v0.4.1..v0.4.2
[0.4.1]: https://github.com/3mdeb/meta-rte/compare/v0.4.0..v0.4.1
[0.4.0]: https://github.com/3mdeb/meta-rte/compare/v0.3.0..v0.4.0

<!-- generated by git-cliff -->
