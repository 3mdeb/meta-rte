DESCRIPTION = "RTE controller"
SECTION = "net"
HOMEPAGE = "https://gitlab.com/3mdeb_rte/RteCtrl/README.md"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "git://git@gitlab.com/3mdeb_rte/RteCtrl.git;protocol=ssh;branch=master"
SRCREV = "${AUTOREV}"
PV = "0.1+${SRCREV}"

DEPENDS += "github.com-gorilla-mux"

GO_IMPORT = "3mdeb/RteCtrl"
GO_INSTALL = "${GO_IMPORT}"

inherit go

do_install_append() {
    # configuration file
    install -d ${D}${sysconfdir}/RteCtrl
    install -m 0644 ${S}/src/${GO_IMPORT}/config/RteCtrl.cfg ${D}${sysconfdir}/RteCtrl/

    # webserver files
    install -d ${D}${datadir}/RteWeb
    install -m 0644 ${S}/src/${GO_IMPORT}/web/index.html ${D}${datadir}/RteWeb
    install -m 0644 ${S}/src/${GO_IMPORT}/web/md5.js ${D}${datadir}/RteWeb
}
