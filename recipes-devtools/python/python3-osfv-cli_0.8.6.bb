SUMMARY = "Open Source Firmware Validation Command Line Interface Tool"
HOMEPAGE = "https://github.com/Dasharo/osfv-scripts"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${WORKDIR}/git/LICENSE;md5=f0b5d75009c8f5041005cdcbe5c58960"

SRC_URI = "git://github.com/Dasharo/osfv-scripts.git;protocol=https;branch=main"
SRCREV = "3b32ed1778408a7d7e328174d40df4ca8c7090c1"

S = "${WORKDIR}/git/osfv_cli"

inherit python_poetry_core

FILES:${PN}:append = " \
    /home/root/.osfv/snipeit.yml \
    /home/root/.osfv/zabbix.yml \
"

RDEPENDS:${PN} += " \
    python3-pyyaml \
    python3-importlib-resources \
    python3-paramiko \
    python3-pexpect \
    python3-requests \
    python3-unidecode \
    python3-voluptuous \
    python3-typer \
"

do_install:append() {
    install -D -m 644 "${S}/snipeit.yml" "${D}/home/root/.osfv/snipeit.yml"
    install -D -m 644 "${S}/zabbix.yml" "${D}/home/root/.osfv/zabbix.yml"
    sed -i 's/YOUR_USER_ID/0/' "${D}/home/root/.osfv/snipeit.yml"
}
