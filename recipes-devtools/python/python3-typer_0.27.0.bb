SUMMARY = "Typer, build great CLIs. Easy to code. Based on Python type hints."
DESCRIPTION = "\
    Typer is a library for building CLI applications that users will love using and developers will love creating. Based on Python type hints. \
    It's also a command line tool to run scripts, automatically converting them to CLI applications. \
"
HOMEPAGE = "https://github.com/fastapi/typer"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=173d405eb704b1499218013178722617"

DEPENDS += "python3-pdm-backend-native"

SRC_URI += "file://0001-fix-license-metadata-for-old-pdm-backend.patch"
SRC_URI[sha256sum] = "629bd12ea5d13a17148125d9a264f949eb171fb3f120f9b04d85873cab054fa5"

inherit pypi python_pep517

PYPI_PACKAGE = "typer"

RDEPENDS:${PN} += "\
    python3-shellingham \
    python3-rich \
    python3-annotated-doc \
"
