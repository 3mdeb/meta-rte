FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += " \
    file://RteCtrl.cfg \
    file://RteCtrl.service \
    file://rte_ctrl.sh \
    "

FILES_${PN} += " \
    ${sysconfdir}/RteCtrl.cfg \
    ${systemd_unitdir}/system/RteCtrl.service \
    ${bindir}/rte_ctrl.sh \
    "

do_install_append() {
    # configuration file
    install -d ${D}${sysconfdir}/RteCtrl
    install -m 0644 ${WORKDIR}/RteCtrl.cfg ${D}${sysconfdir}/RteCtrl/

    install -d ${D}${systemd_unitdir}/system/
    install -m 0644 ${WORKDIR}/RteCtrl.service ${D}${systemd_unitdir}/system/

    install -d ${D}{bindir}
    install -m 0755 ${WORKDIR}/rte_ctrl.sh ${D}${bindir}
}

SYSTEMD_PACKAGES = "${PN}"

SYSTEMD_SERVICE_${PN} = "RteCtrl.service"

SYSTEMD_AUTO_ENABLE_${PN} = "enable"

inherit systemd
