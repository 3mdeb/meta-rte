FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
#
# 4.15.7
PV = "4.15.7+git${SRCPV}"
SRCREV_pn-${PN} = "cb4a115a42867def71fdcbf0d7b714f268ff37fd"

KBRANCH = "linux-4.15.y"

SRC_URI = " \
    git://git.kernel.org/pub/scm/linux/kernel/git/stable/linux-stable.git;protocol=git;branch=${KBRANCH} \
    file://defconfig \
    file://0001-sun8i-h2-plus-orangepi-zero.dts-enable-ehci-ohci-2-3.patch \
    file://0002-sun8i-h2-plus-orangepi-zero.dts-enable-spi1.patch \
    file://0003-sun8i-h2-plus-orangepi-zero.dts-enable-uart2.patch \
    file://0004-sun8i-h2-plus-orangepi-zero.dts-enable-i2c1.patch \
    "
