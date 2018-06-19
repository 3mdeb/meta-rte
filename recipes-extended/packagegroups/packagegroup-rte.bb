SUMMARY = "RTE support packagegroup"
DESCRIPTION = "Support packagegroups which allow to take advantage of the \
Remote Testing Environment board features"
PR = "r1"

inherit packagegroup

PACKAGES = " \
    packagegroup-rte-core \
    packagegroup-rte-imx \
    packagegroup-rte-coreboot \
    "

# core system components
RDEPENDS_packagegroup-rte-core = " \
    bash \
    ser2net \
    minicom \
    tmux \
    systemd \
    udev \
    usbutils \
    inetutils-telnet \
    avahi-daemon \
    3mdeb-rtectrl \
    i2c-tools \
    "

# packages useful for i.MX platforms testing
RDEPENDS_packagegroup-rte-imx = " \
    can-utils \
    android-tools \
    imx-usb-loader \
    "

# packages useful for testing coreboot on various platforms
RDEPENDS_packagegroup-rte-coreboot = " \
    flashrom \
    ifdtool \
    cbfstool \
    "
