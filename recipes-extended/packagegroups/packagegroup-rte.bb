SUMMARY = "RTE support packagegroup"
DESCRIPTION = "Support packagegroup which allows to take advantage of Remote \
               Testing Environment board features"
PR = "r1"

inherit packagegroup

PACKAGES = " \
    packagegroup-core-rte \
    "

RDEPENDS_packagegroup-core-rte = " \
    ser2net \
    flashrom \
    minicom \
    systemd \
    udev \
    usbutils \
    inetutils-telnet \
    "
