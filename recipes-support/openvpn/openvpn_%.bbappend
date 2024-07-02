FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

PACKAGES =+ "${PN}-rte"

RTE_VPN_CONFIG_DIR = "/data/openvpn"

FILES:${PN}-rte = "${RTE_VPN_CONFIG_DIR}"

do_install:prepend() {
    sed -i 's/CONFIG_DIR=\/etc/CONFIG_DIR=\/storage/' ${WORKDIR}/openvpn
}

do_install:append() {
    install -d ${D}/${RTE_VPN_CONFIG_DIR}
}

SYSTEMD_SERVICE:${PN}-rte = "openvpn@rte.service"
SYSTEMD_PACKAGES += "${PN}-rte"
SYSTEMD_AUTO_ENABLE:${PN}-rte = "disable"
