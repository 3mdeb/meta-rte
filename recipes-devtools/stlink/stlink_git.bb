SUMMARY = "stm32 discovery line linux programmer"
HOMEPAGE = "https://github.com/texane/stlink"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=3374725a40df7047d33d627811b8149e"

PV = "1.7.0+git${SRCPV}"

DEPENDS = "libusb"

SRC_URI = " \
    git://github.com/stlink-org/stlink.git;protocol=https;branch=master \
    "
SRCREV = "179650df40295b0af3a148ddfb2c467a0334f54a"

S = "${WORKDIR}/git"

EXTRA_OECMAKE = " \
    -DSTLINK_UDEV_RULES_DIR=${sysconfdir}/udev/rules.d \
    -DSTLINK_MODPROBED_DIR=${sysconfdir}/modprobe.d \
    -DLIB_INSTALL_DIR:PATH="${@os.path.relpath(d.getVar('libdir'), d.getVar('prefix'))}" \
    "

inherit cmake pkgconfig
