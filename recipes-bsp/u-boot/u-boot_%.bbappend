FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

# if fw_env.config file is provided, it will be installed into ${sysconfigdir}
# - see u-boot.inc file
SRC_URI:append = " \
    file://0001-orangepi_zero_defconfig-lower-DRAM_CLK-to-408.patch \
    file://0002-orangepi_zero_defconfig-configure-CLK_FREQ.patch \
    file://0003-sun8i-disable-Vendor-Parameter-Protection.patch \
    file://fw_env.config \
    file://boot.cmd \
    file://random-ethaddr.cfg \
"
