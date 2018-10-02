SUMMARY = "RTE support packagegroup"
DESCRIPTION = "Support packagegroups which allow to take advantage of the \
Remote Testing Environment board features"
PR = "r1"

inherit packagegroup

PACKAGES = " \
    packagegroup-rte-core \
    packagegroup-rte-utils \
    packagegroup-rte-imx \
    packagegroup-rte-stm \
    packagegroup-rte-coreboot \
    "

# core system components
RDEPENDS_packagegroup-rte-core = " \
    kernel-modules \
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
    openssh-sftp-server \
    "

RDEPENDS_packagegroup-rte-utils = " \
    e2fsprogs-e2fsck \
    e2fsprogs-mke2fs \
    e2fsprogs-badblocks \
    e2fsprogs-resize2fs \
    wget \
    ca-certificates \
    coreutils \
    bzip2 \
    gzip \
    zlib \
    tar \
    util-linux \
    tzdata \
    sysfsutils \
    grep \
    gawk \
    sed \
    procps \
    psmisc \
    iproute2 \
    iputils \
    less \
    nfs-utils \
    "

# packages useful for i.MX platforms testing
RDEPENDS_packagegroup-rte-imx = " \
    can-utils \
    android-tools \
    imx-usb-loader \
    udev-rules-rte \
    "

# packages useful for STM32 MCUs testing
RDEPENDS_packagegroup-rte-stm = " \
    stlink \
    "

# packages useful for testing coreboot on various platforms
RDEPENDS_packagegroup-rte-coreboot = " \
    flashrom \
    ifdtool \
    cbfstool \
    "
