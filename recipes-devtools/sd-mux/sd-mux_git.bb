SUMMARY = "Control software for sd-mux devices."
HOMEPAGE = "https://github.com/3mdeb/sd-mux"
LICENSE = "Apachev2"
LIC_FILES_CHKSUM = " \
    file://LICENSE;md5=064ad0ceb0d2be1dd2e7fc4ba1b9de6f \
    file://debian/copyright;md5=9e761451917adf0a2bc35d987cc88a9d \
"

DEPENDS = " \
    libftdi \
    popt \
"

PV = "0.0.2+git${SRCPV}"
SRC_URI = "git://github.com/3mdeb/sd-mux.git;protocol=https;branch=master"
SRCREV = "9dd189d973da64e033a0c5c2adb3d94b23153d94"

S = "${WORKDIR}/git"
inherit cmake pkgconfig
