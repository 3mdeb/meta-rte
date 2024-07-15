LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://${S}/src/github.com/throttled/throttled/v2/LICENSE;md5=626c200490e0119689126e09ecc273a4"

GO_IMPORT = "github.com/throttled/throttled/v2"

SRC_URI = "git://github.com/throttled/throttled"
SRCREV = "eef0076db706fe4531e6efe7dea5cfc402593562"
PV = "2.9.1"

RDEPENDS:${PN} += " \
    bash \
    "

RDEPENDS:${PN}-dev += " \
    bash \
    "

GO_INSTALL = "${GO_IMPORT}"

inherit go-mod
