SUMMARY = "Package implements rate limiting access to resources such as HTTP endpoints."
HOMEPAGE = "https://github.com/throttled/throttled"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://${S}/src/github.com/throttled/throttled/v2/LICENSE;md5=626c200490e0119689126e09ecc273a4"

GO_IMPORT = "github.com/throttled/throttled/v2"

PV = "2.9.1"
SRC_URI = "git://github.com/throttled/throttled;protocol=https;branch=master"
SRCREV = "eef0076db706fe4531e6efe7dea5cfc402593562"

RDEPENDS:${PN} += " \
    bash \
    "

RDEPENDS:${PN}-dev += " \
    bash \
    "

GO_INSTALL = "${GO_IMPORT}"

inherit go-mod
