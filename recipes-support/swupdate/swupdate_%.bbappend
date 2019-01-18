FILESEXTRAPATHS_prepend := "${THISDIR}/swupdate:"

DEPENDS += "swu-confirm"

do_install_append() {
    install -d ${D}${datadir}
    install -m 0600 ${DEPLOY_DIR_IMAGE}/swu-confirm-orange-pi-zero.swu ${D}${datadir}/swu-confirm.swu
}

FILES_${PN} += "${datadir}/swu-confirm.swu"
