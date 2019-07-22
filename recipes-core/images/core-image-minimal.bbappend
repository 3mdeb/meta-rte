IMAGE_FEATURES_append = " ssh-server-openssh"

IMAGE_INSTALL_append = " \
   packagegroup-rte-core \
   packagegroup-rte-utils \
   packagegroup-rte-imx \
   packagegroup-rte-stm \
   packagegroup-rte-coreboot \
   locale-base-en-us \
   python3-setuptools \
   python3-wakeonlan \
   "

# set root password
inherit extrausers

RTE_ROOT_PWD = "meta-rte"
EXTRA_USERS_PARAMS = "usermod -P ${RTE_ROOT_PWD} root;"
