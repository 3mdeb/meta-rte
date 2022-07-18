DESCRIPTION = "stm32 discovery line linux programmer"
HOMEPAGE = "https://github.com/texane/stlink"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://${S}/LICENSE;md5=a119cf258a06d6a2c7f4da37ca03e341"

BRANCH = "master"
TAG = "v1.5.0"

SRC_URI = " \
    git://github.com/stlink-org/stlink.git;protocol=https;branch=${BRANCH};tag=${TAG} \
    file://0001-remove-CMAKE_LIBRARY_PATH-from-CMakeLists-path.patch \
    "

SRCPV = "${TAG}"

S = "${WORKDIR}/git"

DEPENDS = "libusb"

EXTRA_OECMAKE = " \
    -DSTLINK_UDEV_RULES_DIR=${sysconfdir}/udev/rules.d \
    -DSTLINK_MODPROBED_DIR=${sysconfdir}/modprobe.d \
    -DLIB_INSTALL_DIR:PATH="${@os.path.relpath(d.getVar('libdir'), d.getVar('prefix'))}" \
    "

inherit cmake pkgconfig
