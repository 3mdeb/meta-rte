do_install_append () {

    # remove 'interfaces' file. We are using systemd-networkd to bring up the network
    rm -f ${D}${sysconfdir}/network/interfaces

}
