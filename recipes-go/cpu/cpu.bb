DESCRIPTION = "cpu command in Go, inspired by the Plan 9 cpu command"
HOMEPAGE = "https://github.com/u-root/cpu"

LICENSE = "BSD-3-Clause" 
LIC_FILES_CHKSUM = " \
file://${COMMON_LICENSE_DIR}/BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9 \
"

SRC_URI = "file://cpud"

S = "${WORKDIR}"

do_install() {
    install -d ${D}${bindir}   
    install -m 755 ${S}/cpud ${D}${bindir}/ 
}
