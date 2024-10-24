FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += " \
    file://confirm-upgrade.service \
    file://rte-upgrade \
"

FILES:${PN} += " \
    ${sbindir}/rte-upgrade \
    ${systemd_unitdir}/system/confirm-upgrade.service \
"

RDEPENDS:${PN} = "swu-confirm"

do_install:append() {
    install -d ${D}${sbindir}
    install -m 0700 ${WORKDIR}/rte-upgrade ${D}${sbindir}

    install -d ${D}${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/confirm-upgrade.service ${D}${systemd_unitdir}/system
}

SYSTEMD_SERVICE:${PN} += " \
    confirm-upgrade.service \
"
