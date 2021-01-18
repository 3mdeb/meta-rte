SUMMARY = "A small python module for wake on lan."
HOMEPAGE = "https://github.com/remcohaszing/pywakeonlan"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=53755f7472520005b05845f37fe32c84"

SRC_URI = "https://github.com/3mdeb/pywakeonlan/releases/download/${PV}/wakeonlan-${PV}.tar.gz"
SRC_URI[sha256sum] = "ab8c8345b43b82a6864ddd6df8f575e6bacbe367e265b6259d5cc47dd2347e2f"
SRC_URI[sha512sum] = "57a2ef2c64085ec1d5cd48669aba5d1fadb0b6b78b69cba18e31c10074334bf9690e771f7b73c3464981b02de664077cdc0b1ff3e773b1eefbdfcfb372f20364"

S = "${WORKDIR}/wakeonlan-${PV}"

inherit setuptools3

RDEPENDS_${PN} += "python3-core python3-io python3-pkg-resources"
