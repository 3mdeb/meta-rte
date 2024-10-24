# nooelint: oelint.file.underscores
SUMMARY = "udev rules for the RTE board"
HOMEPAGE = "https://3mdeb.com/open-source-hardware/"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = " file://51-usb-converters.rules"

S = "${WORKDIR}"

do_install () {
    install -d ${D}${sysconfdir}/udev/rules.d
    install -m 0644 ${WORKDIR}/51-usb-converters.rules ${D}${sysconfdir}/udev/rules.d/
}
