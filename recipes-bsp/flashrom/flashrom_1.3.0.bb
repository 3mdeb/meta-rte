SUMMARY = "flashrom is a utility for identifying, reading, writing, verifying and erasing flash chips"
HOMEPAGE = "http://flashrom.org"
LICENSE = "GPLv2"

LIC_FILES_CHKSUM = "file://COPYING;md5=751419260aa954499f7abaabaa882bbe"
DEPENDS = "pciutils libusb libusb-compat"

SRC_URI = "https://download.flashrom.org/releases/flashrom-v${PV}.tar.bz2"
SRC_URI[md5sum] = "dd2727f8fa05a4517689ca4f9d87e600"
SRC_URI[sha256sum] = "a053234453ccd012e79f3443bdcc61625cf97b7fd7cb4cdd8bfbffbe8b149623"

S = "${WORKDIR}/flashrom-v${PV}"

inherit pkgconfig

do_install() {
    oe_runmake PREFIX=${prefix} DESTDIR=${D} install
}
