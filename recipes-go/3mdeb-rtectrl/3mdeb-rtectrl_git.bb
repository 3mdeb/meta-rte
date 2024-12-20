SUMMARY = "REST API controller for the RTE board"
HOMEPAGE = "https://github.com/3mdeb/RteCtrl/blob/master/README.md"
SECTION = "rte"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${S}/src/${GO_IMPORT}/LICENSE;md5=50335162666472aa33383f35cce3e355"

DEPENDS += " \
    github.com-gorilla-mux \
    github.com-throttled-throttled \
    github.com-hashicorp-golang-lru \
"

PV = "0.5.3"
SRC_URI = "git://git@github.com/3mdeb/RteCtrl.git;protocol=https;branch=master"
SRCREV = "9910a7254dae317fdcc0afebf91e3985a0622005"

FILES:${PN} += " \
    ${datadir}/RteWeb/* \
    ${bindir}/rte_ctrl \
    "

RDEPENDS:${PN} += " \
    bash \
    "

RDEPENDS:${PN}-dev += " \
    bash \
    "

GO_IMPORT = "3mdeb/RteCtrl"
GO_INSTALL = "${GO_IMPORT}"

inherit go-mod

do_install:append() {
    # webserver files
    install -d ${D}${datadir}/RteWeb
    install -m 0644 ${S}/src/${GO_IMPORT}/web/index.html ${D}${datadir}/RteWeb
    install -m 0644 ${S}/src/${GO_IMPORT}/web/md5.js ${D}${datadir}/RteWeb

    # rte_ctrl script
    install -d ${D}{bindir}
    install -m 0755 ${S}/src/${GO_IMPORT}/scripts/rte_ctrl ${D}${bindir}
}
