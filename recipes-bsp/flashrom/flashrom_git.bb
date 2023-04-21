DESCRIPTION = "flashrom is a utility for identifying, reading, writing, verifying and erasing flash chips"
LICENSE = "GPLv2"
HOMEPAGE = "http://flashrom.org"

LIC_FILES_CHKSUM = "file://COPYING;md5=751419260aa954499f7abaabaa882bbe"

SRC_URI = " \
    git://github.com/flashrom/flashrom.git;branch=master;protocol=https \
    file://flashchips-add-support-for-MX77L25650F.diff \
"
SRCREV = "7ffa626d1278a167c430c55891bbb6f979a5ab92"

S = "${WORKDIR}/git"

inherit pkgconfig

do_install() {
    oe_runmake PREFIX=${prefix} DESTDIR=${D} install
}

# TODO: this verions of flashrom uses different switches than what's in the
# recipe v1.2
PACKAGECONFIG ??= "pci usb ftdi"
PACKAGECONFIG[ftdi] = ",,libftdi"
PACKAGECONFIG[pci] = "-Dpciutils=true,-Dpciutils=false,pciutils"
PACKAGECONFIG[usb] = "-Dusb=true,-Dusb=false,libusb"

# W/A for master flashrom for MX77L25650F
FILES_${PN} += " \
    ${datadir}/bash-completion \
"
