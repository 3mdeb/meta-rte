SUMMARY = "A library to abstract stream I/O like serial port, TCP, telnet, etc"
HOMEPAGE = "https://github.com/cminyard/gensio"
LICENSE = "GPL-2.0-or-later & LGPL-2.1-or-later"
LIC_FILES_CHKSUM = " \
    file://COPYING.LIB;md5=4fbd65380cdd255951079008b364516c \
    file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
    "
SRC_URI = "git://github.com/cminyard/gensio;protocol=https;branch=master"

SRC_URI = "${SOURCEFORGE_MIRROR}/project/ser2net/ser2net/gensio-${PV}.tar.gz"

SRC_URI[sha256sum] = "900f8f83b76ba16f8c7839911bce77354e41f296f9fa677d7945d44ee8358276"

inherit autotools

PACKAGECONFIG ??= "openssl tcp-wrappers"

PACKAGECONFIG[openssl] = "--with-openssl=${STAGING_DIR_HOST}${prefix},--without-openssl, openssl"
PACKAGECONFIG[tcp-wrappers] = "--with-tcp-wrappers,--without-tcp-wrappers, tcp-wrappers"
PACKAGECONFIG[swig] = "--with-swig,--without-swig, swig"

EXTRA_OECONF = "--without-python"

RDEPENDS:${PN} += "bash"
