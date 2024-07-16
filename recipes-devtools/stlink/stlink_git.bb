DESCRIPTION = "stm32 discovery line linux programmer"
HOMEPAGE = "https://github.com/texane/stlink"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://${S}/LICENSE.md;md5=3374725a40df7047d33d627811b8149e"

BRANCH = "master"
TAG = "v1.7.0"

SRC_URI = " \
    git://github.com/stlink-org/stlink.git;protocol=https;branch=${BRANCH};tag=${TAG} \
    "

SRCPV = "${TAG}"

S = "${WORKDIR}/git"

DEPENDS = "libusb"

EXTRA_OECMAKE = " \
    -DSTLINK_UDEV_RULES_DIR=${sysconfdir}/udev/rules.d \
    -DSTLINK_MODPROBED_DIR=${sysconfdir}/modprobe.d \
    -DLIB_INSTALL_DIR:PATH="${@os.path.relpath(d.getVar('libdir'), d.getVar('prefix'))}" \
    "

inherit cmake pkgconfig
