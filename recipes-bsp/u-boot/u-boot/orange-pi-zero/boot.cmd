setenv silent yes

# if fback is not defined yet, boot from 2 partition as default
if test x${fback} != x; then
    boot_part=${fback}
else
    boot_part=2
fi

# Boot once into new system after update
if test x${next_entry} != x; then
    boot_part=${next_entry}
    setenv next_entry
fi

saveenv

setenv bootargs console=${console} console=tty1 root=/dev/mmcblk0p${boot_part} rootwait panic=10 ${extra}
ext4load mmc 0:${boot_part} ${fdt_addr_r} /boot/${fdtfile}
ext4load mmc 0:${boot_part} ${kernel_addr_r} /boot/zImage
bootz ${kernel_addr_r} - ${fdt_addr_r} || bootm ${kernel_addr_r} - ${fdt_addr_r}
