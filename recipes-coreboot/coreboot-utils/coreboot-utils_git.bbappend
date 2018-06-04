FILESEXTRAPATHS_prepend := "${THISDIR}/coreboot-utils:"

SRC_URI = "gitsm://github.com/coreboot/coreboot.git;protocol=https;branch=4.8_branch"

# checkout 4.8.1 tag
SRCREV = "6794ce02d45273427c1c6675950c8468380c040a"

SRC_URI = " \
    gitsm://github.com/coreboot/coreboot.git;protocol=https;branch=4.8_branch \
    file://0001-utils-do-not-override-compiler-variables.patch \
    "


