SUMMARY = "Utility for detecting, reading, writing, verifying and erasing flash chips."
HOMEPAGE = "https://flashprog.org/wiki/Flashprog"
SECTION = "devel"
LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://COPYING;md5=751419260aa954499f7abaabaa882bbe"

DEPENDS = "libusb"

PV = "1.3"
SRC_URI = "git://github.com/SourceArcade/flashprog.git;protocol=https;branch=main;tag=v${PV}"

EXTRA_OEMAKE = "NOSTRIP=1"

S = "${WORKDIR}/git"

do_install () {
    oe_runmake DESTDIR=${D} PREFIX=/usr install
}

FILES:${PN} += " \
    ${datadir}/FlashProg \
"

inherit pkgconfig
