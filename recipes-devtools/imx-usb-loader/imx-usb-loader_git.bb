SUMMARY = "USB & UART loader for i.MX5/6/7/8 series"
HOMEPAGE = "https://github.com/boundarydevices/imx_usb_loader"
SECTION = "devel"
LICENSE = "LGPL-2.1-or-later"
LIC_FILES_CHKSUM = "file://COPYING;md5=4fbd65380cdd255951079008b364516c"

DEPENDS = "libusb"

PV = "1.0+${SRCPV}"
SRC_URI = "git://github.com/boundarydevices/imx_usb_loader.git;protocol=https;branch=master"
SRCREV = "e5394615dd413c3823d5bd1de340933e16a8c07c"

S = "${WORKDIR}/git"

do_install () {
    oe_runmake DESTDIR=${D} install
}

BBCLASSEXTEND = "native nativesdk"

inherit pkgconfig
