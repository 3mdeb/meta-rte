FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += " \
    file://confirm-upgrade.service \
    file://rte-upgrade \
"

RDEPENDS_${PN} = "swu-confirm"

do_install_append() {
    install -d ${D}${sbindir}
    install -m 0700 ${WORKDIR}/rte-upgrade ${D}${sbindir}

    install -d ${D}${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/confirm-upgrade.service ${D}${systemd_unitdir}/system
}

FILES_${PN} += " \
    ${sbindir}/rte-upgrade \
    ${systemd_unitdir}/system/confirm-upgrade.service \
"

SYSTEMD_SERVICE_${PN} += " \
    confirm-upgrade.service \
"
