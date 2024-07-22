SUMMARY = "cbfstool and ifdtool utilities for working with coreboot firmware"
HOMEPAGE = "https://github.com/coreboot/coreboot/blob/main/README.md"
SECTION = "coreboot"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=751419260aa954499f7abaabaa882bbe"

PV = "0.1+${SRCREV}"

SRC_URI = " \
    gitsm://github.com/coreboot/coreboot.git;protocol=https;branch=4.8_branch \
    file://0001-utils-do-not-override-compiler-variables.patch \
    "

# checkout 4.8.1 tag
SRCREV = "6794ce02d45273427c1c6675950c8468380c040a"

# this indicates the folder to run do_compile from.
S = "${WORKDIR}/git"

PACKAGES =+ "cbfstool ifdtool"

FILES:cbfstool += "${bindir}/cbfstool"
FILES:ifdtool += "${bindir}/ifdtool"

INSANE_SKIP:cbfstool = "ldflags"
INSANE_SKIP:ifdtool = "ldflags"

# this command will be run to compile your source code. it assumes you are in the
# directory indicated by S. i'm just going to use make and rely on my Makefile
do_compile () {
    oe_runmake -C util/cbfstool
    oe_runmake -C util/ifdtool
}

# this will copy the compiled file and place it in ${bindir}, which is /usr/bin
do_install () {
    install -d ${D}${bindir}
    install -m 0755 ${S}/util/cbfstool/cbfstool ${D}${bindir}
    install -m 0755 ${S}/util/ifdtool/ifdtool ${D}${bindir}
}
