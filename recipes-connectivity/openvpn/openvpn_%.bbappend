FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

PACKAGES =+ "${PN}-rte"

RTE_VPN_CONFIG_DIR = "/storage/openvpn"

FILES_${PN}-rte = "${RTE_VPN_CONFIG_DIR}"

do_install_prepend() {
    sed -i 's/CONFIG_DIR=\/etc/CONFIG_DIR=\/storage/' ${WORKDIR}/openvpn
}

do_install_append() {
    install -d ${D}/${RTE_VPN_CONFIG_DIR}
}

SYSTEMD_SERVICE_${PN}-rte = "openvpn@rte.service"
SYSTEMD_PACKAGES += "${PN}-rte"
SYSTEMD_AUTO_ENABLE_${PN}-rte = "enable"
