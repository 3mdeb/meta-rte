FILESEXTRAPATHS_prepend := "${THISDIR}/openocd:"

SRC_URI += " \
    file://openocd.cfg \
    file://orangepi.cfg \
    "
do_install_append() {

    install -m 0644 ${WORKDIR}/openocd.cfg ${D}${datadir}/openocd/scripts/
    install -m 0644 ${WORKDIR}/orangepi.cfg ${D}${datadir}/openocd/scripts/interface/
}
