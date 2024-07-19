FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"
SRC_URI += "file://minirc.dfl"

do_install:append() {
    install -d ${D}${sysconfdir}
    install -m 0644 ${WORKDIR}/minirc.dfl ${D}${sysconfdir}/minirc.dfl
}
