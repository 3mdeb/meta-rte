DESCRIPTION = "RTE controller"
SECTION = "rte"
HOMEPAGE = "https://github.com/3mdeb/RteCtrl/blob/master/README.md"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${S}/src/${GO_IMPORT}/LICENSE;md5=50335162666472aa33383f35cce3e355"

TAG = "0.5.1"
SRC_URI = "git://git@github.com/3mdeb/RteCtrl.git;protocol=https;branch=master;tag=${TAG}"
PV = "${TAG}"

DEPENDS += "github.com-gorilla-mux"

GO_IMPORT = "3mdeb/RteCtrl"
GO_INSTALL = "${GO_IMPORT}"

inherit go

FILES_${PN} += "\
    ${datadir}/RteWeb/* \
    "

do_install_append() {
    # webserver files
    install -d ${D}${datadir}/RteWeb
    install -m 0644 ${S}/src/${GO_IMPORT}/web/index.html ${D}${datadir}/RteWeb
    install -m 0644 ${S}/src/${GO_IMPORT}/web/md5.js ${D}${datadir}/RteWeb
}
