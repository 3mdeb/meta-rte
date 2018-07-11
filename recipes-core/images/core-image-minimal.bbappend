IMAGE_FEATURES_append = " ssh-server-openssh"

IMAGE_INSTALL_append = " \
   packagegroup-rte-core \
   packagegroup-rte-utils \
   packagegroup-rte-imx \
   packagegroup-rte-coreboot \
   "

# set root password
inherit extrausers

RTE_ROOT_PWD = "meta-rte"
EXTRA_USERS_PARAMS = "usermod -P ${RTE_ROOT_PWD} root;"
