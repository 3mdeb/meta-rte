SUMMARY = "CLI tool to manage OSFV lab devices"
DESCRIPTION = "Installs the osfv_cli tool via Poetry and pip."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

SRC_URI = "git://github.com/Dasharo/osfv-scripts.git;protocol=https;branch=main"
SRCREV = "${AUTOREV}"

S = "${WORKDIR}/git"

inherit python3native setuptools3

DEPENDS += "python3 python3-poetry-core python3-pip python3-pip-native"

do_configure[noexec] = "1"

do_compile () {
    cd "${S}/osfv_cli"

    env \
      PIP_CONFIG_FILE=/dev/null \
      PIP_DISABLE_PIP_VERSION_CHECK=1 \
      ${STAGING_BINDIR_NATIVE}/pip3 install --no-cache-dir poetry-plugin-export

    env \
      PIP_CONFIG_FILE=/dev/null \
      PIP_DISABLE_PIP_VERSION_CHECK=1 \
      ${STAGING_BINDIR_NATIVE}/poetry export \
        -f requirements.txt \
        -o requirements.txt \
        --without-hashes

    mkdir -p "${WORKDIR}/wheels"
    env \
      PIP_CONFIG_FILE=/dev/null \
      PIP_DISABLE_PIP_VERSION_CHECK=1 \
      ${STAGING_BINDIR_NATIVE}/python3 -m pip wheel \
        --isolated --no-cache-dir --no-deps \
        --wheel-dir="${WORKDIR}/wheels" \
        -r requirements.txt

    env \
      PIP_CONFIG_FILE=/dev/null \
      PIP_DISABLE_PIP_VERSION_CHECK=1 \
      ${STAGING_BINDIR_NATIVE}/python3 -m pip wheel \
        --isolated --no-cache-dir --no-deps \
        --wheel-dir="${WORKDIR}/wheels" \
        .

    mkdir -p "${WORKDIR}/install"
    env \
      PIP_CONFIG_FILE=/dev/null \
      PIP_DISABLE_PIP_VERSION_CHECK=1 \
      ${STAGING_BINDIR_NATIVE}/python3 -m pip install \
        --isolated \
        --no-cache-dir \
        --no-deps \
        --ignore-installed \
        --upgrade \
        --target="${WORKDIR}/install" \
        "${WORKDIR}/wheels/"*.whl
}

do_install () {
    cp -r "${WORKDIR}/install"/* "${D}/"
}

FILES:${PN} += " \
    /usr/local/bin/* \
    /usr/bin/* \
"
