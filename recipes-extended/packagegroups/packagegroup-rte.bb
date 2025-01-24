SUMMARY = "RTE support packagegroup"
DESCRIPTION = " \
    Support packagegroups which allow to take advantage of the \
    Remote Testing Environment board features \
    "
PR = "r1"

PACKAGE_ARCH = "${TUNE_PKGARCH}"

inherit packagegroup

PACKAGES = " \
    packagegroup-rte-core \
    packagegroup-rte-utils \
    packagegroup-rte-imx \
    packagegroup-rte-stm \
    packagegroup-rte-coreboot \
    "

# core system components
RDEPENDS:packagegroup-rte-core = " \
    kernel-modules \
    bash \
    curl \
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
    swupdate \
    u-boot \
    openvpn \
    xradio-firmware \
    xradio \
    dcu \
    "

RDEPENDS:packagegroup-rte-utils = " \
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
    sd-mux \
    sed \
    procps \
    psmisc \
    iproute2 \
    iputils \
    less \
    vim-tiny \
    nfs-utils \
    bmaptool \
    bc \
    python3 \
    python3-modules \
    python3-wakeonlan \
    wpa-supplicant \
    cpu \
    xcb-proto-dev \
    rte-persistent-data \
    rte-tests \
    "

# packages useful for i.MX platforms testing
RDEPENDS:packagegroup-rte-imx = " \
    can-utils \
    android-tools \
    imx-usb-loader \
    udev-rules-rte \
    "

# packages useful for STM32 MCUs testing
RDEPENDS:packagegroup-rte-stm = " \
    openocd \
    stlink \
    "

# packages useful for testing coreboot on various platforms
RDEPENDS:packagegroup-rte-coreboot = " \
    flashrom \
    dediprog-flasher \
    flashprog-flasher \
    ifdtool \
    cbfstool \
    "
