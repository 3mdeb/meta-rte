FILESEXTRAPATHS_prepend := "${THISDIR}/swupdate:"

SRC_URI_append = " \
    file://sw-versions \
    "

FILES_${PN} += " \
    ${sysconfdir}/sw-versions \
    "

do_set_version () {
    sed -i 's/DISTRO_VERSION/${DISTRO_VERSION}/' ${WORKDIR}/sw-versions
}

do_install_append () {
    install -m 0644 ${WORKDIR}/sw-versions ${D}/${sysconfdir}
}

addtask do_set_version after do_compile before do_install
