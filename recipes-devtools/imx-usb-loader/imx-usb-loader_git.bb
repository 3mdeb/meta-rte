DESCRIPTION = "USB & UART loader for i.MX5/6/7/8 series"
SECTION = "devel"
LICENSE = "LGPL-2.1-or-later"
LIC_FILES_CHKSUM = "file://COPYING;md5=4fbd65380cdd255951079008b364516c"

DEPENDS = "libusb"

SRCREV = "e5394615dd413c3823d5bd1de340933e16a8c07c"
SRC_URI = "git://github.com/boundarydevices/imx_usb_loader.git;protocol=https"

PV = "1.0+${SRCPV}"

S = "${WORKDIR}/git"

do_install () {
    oe_runmake DESTDIR=${D} install
}

BBCLASSEXTEND = "native nativesdk"

inherit pkgconfig
