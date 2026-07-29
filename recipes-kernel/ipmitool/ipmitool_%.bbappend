FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

# The base recipe leaves IANA_ENTERPRISE_NUMBERS empty and configures with
# --disable-registry-download, so no PEN registry reaches the image. Ship a copy
# from the layer, because the checksum of the registry published by IANA changes
# as entries are added.
SRC_URI += "file://enterprise-numbers"

do_install:append() {
    install -d "${D}${datadir}/misc"
    install -m 0644 "${WORKDIR}/enterprise-numbers" "${D}${datadir}/misc/enterprise-numbers"
}
