IMAGE_INSTALL_append = " \
   packagegroup-core-rte \
   "

# set root password
inherit extrausers

RTE_ROOT_PWD = "meta-rte"
EXTRA_USERS_PARAMS = "usermod -P ${RTE_ROOT_PWD} root;"
