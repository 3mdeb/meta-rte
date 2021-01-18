SUMMARY = "Terminal session recorder"
HOMEPAGE = "https://asciinema.org"

LICENSE = "GPLv3 & GPL-3.0+"
LIC_FILES_CHKSUM = "file://LICENSE;md5=d32239bcb673463ab874e80d47fae504"

SRC_URI = "git://github.com/asciinema/asciinema.git;protocol=https;branch=master;tag=v2.0.2"

S = "${WORKDIR}/git"

inherit setuptools3

RDEPENDS_${PN} += "python3-core python3-pkg-resources ncurses"

# WARNING: We were unable to map the following python package/module
# dependencies to the bitbake packages which include them:
#    argparse
#    base64
#    codecs
#    configparser
#    gzip
#    html.parser
#    http
#    io
#    json
#    json.decoder
#    locale
#    multiprocessing
#    os
#    platform
#    queue
#    re
#    shlex
#    signal
#    struct
#    subprocess
#    tempfile
#    termios
#    threading
#    tty
#    urllib.error
#    urllib.parse
#    urllib.request
#    uuid
