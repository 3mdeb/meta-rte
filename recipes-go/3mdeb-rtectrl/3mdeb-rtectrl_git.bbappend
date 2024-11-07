FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append = " \
    file://RteCtrl.cfg \
    file://RteCtrl.service \
    file://flash.sh \
    "

FILES:${PN} += " \
    ${sysconfdir}/RteCtrl.cfg \
    ${systemd_unitdir}/system/RteCtrl.service \
    ${sbindir}/flash.sh \
    "

do_install:append() {
    # configuration file
    install -d ${D}${sysconfdir}/RteCtrl
    install -m 0644 ${WORKDIR}/RteCtrl.cfg ${D}${sysconfdir}/RteCtrl/

    install -d ${D}${systemd_unitdir}/system/
    install -m 0644 ${WORKDIR}/RteCtrl.service ${D}${systemd_unitdir}/system/

    install -d ${D}${sbindir}
    install -m 0755 ${WORKDIR}/flash.sh ${D}${sbindir}/
}

SYSTEMD_PACKAGES = "${PN}"

SYSTEMD_SERVICE:${PN} = "RteCtrl.service"

SYSTEMD_AUTO_ENABLE:${PN} = "enable"

inherit systemd
