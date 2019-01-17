FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

do_install_append() {

    # Do not ship fw_env.config with this recipe. It is shipped with u-boot
    # recipe instead.
    rm ${D}${sysconfdir}/fw_env.config
}
