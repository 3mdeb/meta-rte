FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
#
# 4.16-rc3
PV = "4.16-rc3+git${SRCPV}"
SRCREV_pn-${PN} = "4a3928c6f8a53fa1aed28ccba227742486e8ddcb"

KBRANCH = "master"

SRC_URI = " \
    git://git.kernel.org/pub/scm/linux/kernel/git/torvalds/linux.git;protocol=git;branch=${KBRANCH} \
    file://defconfig \
    "
