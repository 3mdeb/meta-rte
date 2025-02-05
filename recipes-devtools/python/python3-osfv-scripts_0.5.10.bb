SUMMARY = "CLI tool to manage OSFV lab devices"
DESCRIPTION = "This recipe installs the osfv_cli tool."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

SRC_URI = " \
    git://github.com/Dasharo/osfv-scripts.git;protocol=https;branch=main \
"
SRCREV = "${AUTOREV}"

S = "${WORKDIR}/git"

inherit python3native setuptools3

DEPENDS += "python3 python3-poetry-core python3-pip python3-pip-native"

do_configure[noexec] = "1"

do_compile () {
    cd ${S}/osfv_cli

    export PIP_ROOT_USER_ACTION=ignore
    export PYTHONUSERBASE="${WORKDIR}/python-env"
    export PATH="${PYTHONUSERBASE}/bin:$PATH"
    export PYTHONPATH="${PYTHONUSERBASE}/lib/python3.12/site-packages:$PYTHONPATH"

    ${STAGING_BINDIR_NATIVE}/pip3 install --user --no-cache-dir poetry

    ${PYTHONUSERBASE}/bin/poetry env use ${STAGING_BINDIR_NATIVE}/python3 || true

    unset _PYTHON_SYSCONFIGDATA_NAME
    ${PYTHONUSERBASE}/bin/poetry lock
    ${PYTHONUSERBASE}/bin/poetry install --no-root
}

do_install() {
    install -d ${D}/home/root
    cp -r ${S} ${D}/home/root/osfv-scripts
    rm -rf ${D}/home/root/osfv-scripts/.git  # Remove .git to avoid packaging issues
    chown -R root:root ${D}/home/root/osfv-scripts
}

FILES:${PN} += "/home/root/osfv-scripts"
