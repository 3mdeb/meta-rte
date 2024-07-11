DESCRIPTION = "Minicom configuration file"
SECTION = "base"
LICENSE = "CLOSED"

SRC_URI = "file://minirc.dfl"

do_install() {
    install -d ${D}${sysconfdir}
    install -m 0644 ${WORKDIR}/minirc.dfl ${D}${sysconfdir}/minirc.dfl
}
