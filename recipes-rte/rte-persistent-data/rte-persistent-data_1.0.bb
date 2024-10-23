SUMMARY = "Persistent data partition test"
DESCRIPTION = "Data to be placed on persistent data partition"
HOMEPAGE = "https://github.com/3mdeb/meta-rte"
SECTION = "support"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = " \
    file://rte-persistent-data-test.conf \
    "

S = "${WORKDIR}"

FILES:${PN} += " \
    ${PERSISTENT_DATA_DIR}/rte-persistent-data-test.conf \
    "

do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install() {
    install -d ${D}${PERSISTENT_DATA_DIR}
    install -m 0644 rte-persistent-data-test.conf ${D}${PERSISTENT_DATA_DIR}/
}
