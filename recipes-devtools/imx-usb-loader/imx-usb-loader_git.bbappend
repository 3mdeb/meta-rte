FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += " \
    file://imx_usb.conf \
    file://mx6_usb_rom.conf \
    file://mx6_usb_sdp_spl.conf \
    "

do_install_append() {
    #remove default config files
    rm ${D}${sysconfdir}/imx-loader.d/*
    install -m 0644 ${WORKDIR}/imx_usb.conf ${D}${sysconfdir}/imx-loader.d/
    install -m 0644 ${WORKDIR}/mx6_usb_rom.conf ${D}${sysconfdir}/imx-loader.d/
    install -m 0644 ${WORKDIR}/mx6_usb_sdp_spl.conf ${D}${sysconfdir}/imx-loader.d/
}
