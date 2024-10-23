SUMMARY = "flashrom is a utility for identifying, reading, writing, verifying and erasing flash chips"
HOMEPAGE = "http://flashrom.org"
LICENSE = "GPL-2.0-or-later"

LIC_FILES_CHKSUM = "file://COPYING;md5=751419260aa954499f7abaabaa882bbe"
DEPENDS = "pciutils libusb libusb-compat"

SRC_URI = "https://download.flashrom.org/releases/flashrom-${PV}.tar.xz"
SRC_URI[sha256sum] = "ad7ee1b49239c6fb4f8f55e36706fcd731435db1a4bd2fab3d80f1f72508ccee"

S = "${WORKDIR}/flashrom-${PV}"

inherit pkgconfig meson bash-completion
