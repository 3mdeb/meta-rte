SUMMARY = "RTE support packagegroup"
DESCRIPTION = "Support packagegroup which allows to take advantage of Remote \
               Testing Environment board features"
PR = "r1"

inherit packagegroup

PACKAGES = " \
    packagegroup-core-rte \
    "

RDEPENDS_packagegroup-core-rte = " \
    bash \
    ser2net \
    flashrom \
    minicom \
    systemd \
    udev \
    usbutils \
    inetutils-telnet \
    avahi-daemon \
    3mdeb-rtectrl \
    can-utils \
    i2c-tools \
    "
