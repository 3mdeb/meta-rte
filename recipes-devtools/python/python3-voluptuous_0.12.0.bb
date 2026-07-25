# Modified copy of
# https://git.yoctoproject.org/meta-cloud-services/tree/meta-openstack/recipes-devtools/python/python3-voluptuous_0.12.0.bb?h=scarthgap&id=ea865f6965128d61602e7d20e1ab8554ce05633a
SUMMARY = "Voluptuous is a Python data validation library"
HOMEPAGE = "https://pypi.python.org/pypi/voluptuous/"
SECTION = "devel/python"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://COPYING;md5=9855ba150f2edb00d8e7a41554896ffb"

SRC_URI[sha256sum] = "3a4ef294e16f6950c79de4cba88f31092a107e6e3aaa29950b43e2bb9e1bb2dc"

inherit setuptools3 pypi
