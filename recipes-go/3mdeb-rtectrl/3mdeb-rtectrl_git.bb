DESCRIPTION = "RTE controller"
SECTION = "rte"
HOMEPAGE = "https://github.com/3mdeb/RteCtrl/blob/master/README.md"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${S}/src/${GO_IMPORT}/LICENSE;md5=50335162666472aa33383f35cce3e355"

TAG = "0.5.1"
SRC_REV = "3fdbd32906d49d571854ea8604266f18b2795dd9"
SRC_URI = "git://git@github.com/3mdeb/RteCtrl.git;protocol=https;branch=rest-server-rate-limit;rev=${SRC_REV}"
PV = "${TAG}"

DEPENDS += " \
    github.com-gorilla-mux \
    github.com-throttled-throttled \
    github.com-hashicorp-golang-lru \
"

RDEPENDS_${PN} += " \
    bash \
    "

RDEPENDS_${PN}-dev += " \
    bash \
    "

GO_IMPORT = "3mdeb/RteCtrl"
GO_INSTALL = "${GO_IMPORT}"

inherit go

FILES_${PN} += " \
    ${datadir}/RteWeb/* \
    ${bindir}/rte_ctrl \
    "

do_install_append() {
    # webserver files
    install -d ${D}${datadir}/RteWeb
    install -m 0644 ${S}/src/${GO_IMPORT}/web/index.html ${D}${datadir}/RteWeb
    install -m 0644 ${S}/src/${GO_IMPORT}/web/md5.js ${D}${datadir}/RteWeb

    # rte_ctrl script 
    install -d ${D}{bindir}
    install -m 0755 ${S}/src/${GO_IMPORT}/scripts/rte_ctrl ${D}${bindir}
}
