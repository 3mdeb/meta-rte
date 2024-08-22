FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SUMMARY = "A serial to network proxy"
HOMEPAGE = "http://sourceforge.net/projects/ser2net/"
SECTION = "console/network"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=bae3019b4c6dc4138c217864bd04331f"

DEPENDS = "gensio libyaml"

SRC_URI = "${SOURCEFORGE_MIRROR}/project/ser2net/ser2net/ser2net-${PV}.tar.gz"

SRC_URI[sha256sum] = "f5be52033a690bd0dd711209a64ebaec024ee4542b1357350aad8489dc2bf720"

UPSTREAM_CHECK_URI = "http://sourceforge.net/projects/ser2net/files/ser2net"

inherit autotools pkgconfig systemd

SRC_URI:append = " \
    file://ser2net.yaml \
    file://ser2net.service \
    "

FILES:${PN} += " \
    ${sysconfdir}/ser2net.yaml \
    ${systemd_unitdir}/system/ser2net.service \
    "
BBCLASSEXTEND = "native nativesdk"

do_install:append() {
    install -d ${D}${sysconfdir}
    install -m 0644 ${WORKDIR}/ser2net.yaml ${D}${sysconfdir}

    install -d ${D}${systemd_unitdir}/system/
    install -m 0644 ${WORKDIR}/ser2net.service ${D}${systemd_unitdir}/system/
}

SYSTEMD_PACKAGES = "${PN}"

SYSTEMD_SERVICE:${PN} = "ser2net.service"

SYSTEMD_AUTO_ENABLE:${PN} = "enable"
