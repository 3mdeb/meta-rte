SUMMARY = "Utility for detecting, reading, writing, verifying and erasing flash chips."
HOMEPAGE = "https://flashprog.org/wiki/Flashprog"
SECTION = "devel"
LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://COPYING;md5=751419260aa954499f7abaabaa882bbe"

DEPENDS = "libusb pciutils libftdi libgpiod"

PV = "1.0+${SRCPV}"
SRC_URI = "git://github.com/SourceArcade/flashprog.git;protocol=https;branch=main"
SRCREV = "eb2c04185f8f471c768b742d66e4c552effdd9cb"

# Disable JLINK since it is not needed right now.
# To add it we need to port https://github.com/syntacore/libjaylink to Yocto first.
EXTRA_OEMAKE = "CONFIG_JLINK_SPI=no"

S = "${WORKDIR}/git"

do_install () {
    oe_runmake DESTDIR=${D} PREFIX=/usr install
}

FILES:${PN} += " \
    ${datadir}/FlashProg \
"

inherit pkgconfig
