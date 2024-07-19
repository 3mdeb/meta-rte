FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += " \
    file://0001-sun8i-h2-plus-orangepi-zero.dts-enable-i2c1.patch \
    file://0002-sun8i-h2-plus-orangepi-zero.dts-add-mcp23017-node.patch \
    file://0003-sun8i-h2-plus-orangepi-zero.dts-enable-uart2.patch \
    file://0004-sun8i-h2-plus-orangepi-zero.dts-enable-spi1.patch \
    file://0005-sun8i-h2-plus-orangepi-zero.dts-enable-ehci-2-3.patch \
    file://0006-sun8i-h2-plus-orangepi-zero.dts-enable-ohci-2-3.patch \
    file://0007-drivers-spidev-add-spidev-to-compatible-list.patch \
    file://0008-sun8i-h2-plus-orangepi-zero.dts-enable-spidev.patch \
    file://0009-sun8i-h2-plus-orangepi-zero.dts-enable-in219.patch \
    "

SRC_URI += " \
    file://mcp23017.cfg \
    file://can-serial.cfg \
    file://usb-serial.cfg \
    file://magic-sysrq.cfg \
    file://ina-2xx.cfg \
    file://9p.cfg \
    "
