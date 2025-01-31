FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append = " \
    file://RteCtrl.service \
    "

FILES:${PN} += " \
    ${sysconfdir}/RteCtrl.cfg \
    ${systemd_unitdir}/system/RteCtrl.service \
    "

do_install:append() {
    install -d ${D}${systemd_unitdir}/system/
    install -m 0644 ${WORKDIR}/RteCtrl.service ${D}${systemd_unitdir}/system/
}

SYSTEMD_PACKAGES = "${PN}"

SYSTEMD_SERVICE:${PN} = "RteCtrl.service"

SYSTEMD_AUTO_ENABLE:${PN} = "enable"

inherit systemd
