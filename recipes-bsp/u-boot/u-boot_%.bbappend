FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append = " \
    file://0001-orangepi_zero_defconfig-lower-DRAM_CLK-to-408.patch \
    file://0002-orangepi_zero_defconfig-configure-CLK_FREQ.patch \
"
