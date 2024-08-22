IMAGE_FEATURES:append = " ssh-server-openssh"

IMAGE_INSTALL:append = " \
    packagegroup-rte-core \
    packagegroup-rte-utils \
    packagegroup-rte-imx \
    packagegroup-rte-stm \
    packagegroup-rte-coreboot \
    locale-base-en-us \
    "# set root passwordINHERIT += "extrausers"RTE_ROOT_PWD = "meta-rte"EXTRA_USERS_PARAMS = "usermod -P ${RTE_ROOT_PWD} root;"
