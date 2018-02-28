FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += " \
    file://ser2net.conf \
    file://ser2net.service \
    "

FILES_${PN} += " \
    ${sysconfdir}/ser2net.conf \
    ${systemd_unitdir}/system/ser2net.service \
    "

do_install_append() {
    install -d ${D}${sysconfdir}
    install -m 0644 ${WORKDIR}/ser2net.conf ${D}${sysconfdir}

    install -d ${D}${systemd_unitdir}/system/
    install -m 0644 ${WORKDIR}/ser2net.service ${D}${systemd_unitdir}/system/
}

SYSTEMD_PACKAGES = "${PN}"

SYSTEMD_SERVICE_${PN} = "ser2net.service"

SYSTEMD_AUTO_ENABLE_${PN} = "enable"

inherit systemd
