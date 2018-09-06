FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += " \
    file://50-dhcp.network \
    "

do_install_append() {
    install -d ${D}${systemd_unitdir}/network
    install -m 0644 ${WORKDIR}/50-dhcp.network ${D}${systemd_unitdir}/network

    # set hardware watchdog timeout to 15 seconds
    sed -e 's/.*RuntimeWatchdogSec=.*/RuntimeWatchdogSec=15/' -i ${D}${sysconfdir}/systemd/system.conf
}
