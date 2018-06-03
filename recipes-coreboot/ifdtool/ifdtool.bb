DESCRIPTION = "ifdtool - dump Intel Firmware Descriptor information"
SECTION = "coreboot"
HOMEPAGE = "https://github.com/coreboot/coreboot/README.md"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "https://github.com/coreboot/coreboot/COPYING;md5=751419260aa954499f7abaabaa882bbe"

SRC_URI = "gitsm://git@github.com/coreboot/coreboot.git;protocol=https;branch=master"

# checkout 4.8.1 tag
SRCREV = "6794ce02d45273427c1c6675950c8468380c040a"

PV = "0.1+${SRCREV}"

# this indicates the folder to run do_compile from.
S="${WORKDIR}/git"

# this command will be run to compile your source code. it assumes you are in the
# directory indicated by S. i'm just going to use make and rely on my Makefile
do_compile () {
    oe_runmake -C util/ifdtool
}
 
# this will copy the compiled file and place it in ${bindir}, which is /usr/bin
do_install () {
    install -d ${D}${bindir}
    install -m 0755 ${S}/util/ifdtool/ifdtool ${D}${bindir}
}
