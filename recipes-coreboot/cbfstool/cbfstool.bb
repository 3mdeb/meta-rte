DESCRIPTION = "cbfstool - util to manipulate coreboot firmware content"
SECTION = "coreboot"
HOMEPAGE = "https://github.com/coreboot/coreboot/README.md"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"
DEPENDS = ""


SRC_URI = "git://git@github.com/coreboot/coreboot.git;protocol=https;branch=master"

SRCREV = "${AUTOREV}"

PV = "0.1+${SRCREV}"

# this indicates the folder to run do_compile from.
S="${WORKDIR}/git"

# this command will be run to compile your source code. it assumes you are in the
# directory indicated by S. i'm just going to use make and rely on my Makefile
do_compile () {
    git submodule update --init --checkout
    make -C util/cbfstool
}
 
# this will copy the compiled file and place it in ${bindir}, which is /usr/bin
do_install () {
    install -d ${D}${bindir}
    install -m 0755 ${S}/util/cbfstool/cbfstool ${D}${bindir}
}
