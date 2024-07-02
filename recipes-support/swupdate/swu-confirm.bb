DESCRIPTION = "SWU confirm image"
SECTION = "utils"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

SRC_URI = " \
    file://sw-description \
    "

S = "${WORKDIR}"

DEPENDS += "cpio"

SWU_CONFIRM_FILES = "sw-description"
SWU_CONFIRM_NAME = "swu-confirm.swu"

do_compile() {
    for file in ${SWU_CONFIRM_FILES};do
        echo $file;done | cpio -ov -H crc > ${SWU_CONFIRM_NAME}
}

do_install() {
    install -d ${D}${datadir}
    install -m 0600 ${S}/${SWU_CONFIRM_NAME} ${D}${datadir}/
}

FILES:${PN} = "${datadir}/${SWU_CONFIRM_NAME}"
