IMAGE_FEATURES:append = " ssh-server-openssh"

IMAGE_INSTALL:append = " \
    packagegroup-rte-core \
    packagegroup-rte-utils \
    packagegroup-rte-imx \
    packagegroup-rte-stm \
    packagegroup-rte-coreboot \
    locale-base-en-us \
    python3-pip \
    python3-osfv-scripts \
    "

inherit extrausers

# Result of `printf "%q" $(mkpasswd -m sha256crypt meta-rte)`
RTE_ROOT_PWD = "\$5\$8XCtHnklsZSCLoAi\$4lHnmijhTjSPuuvddA9ktyVa5X4nAOXS9M4AWqOrnt1"
EXTRA_USERS_PARAMS = "usermod -p '${RTE_ROOT_PWD}' root"
