SUMMARY = "Linux software for Dediprog SF100 and SF600 SPI flash programmers"
HOMEPAGE = "https://github.com/DediProgSW/SF100Linux"
SECTION = "devel"
LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://LICENSE;md5=a23a74b3f4caf9616230789d94217acb"

DEPENDS = "libusb"

PV = "1.0+${SRCPV}"
SRC_URI = " \
    git://github.com/DediProgSW/SF100Linux.git;protocol=https;branch=master \
    file://0001-add-support-for-cross-compilation.patch \
    file://0002-Makefile-add-conditional-stripping.patch \
    "
SRCREV = "e691f2d432144e3dbc82e9e0eea1ebaed4f3becf"

EXTRA_OEMAKE = "NOSTRIP=1"

S = "${WORKDIR}/git"

do_install () {
    oe_runmake DESTDIR=${D} PREFIX=/usr install
}

FILES:${PN} += " \
    ${datadir}/DediProg \
"

inherit pkgconfig
