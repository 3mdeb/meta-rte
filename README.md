# meta-rte

# Meta-RTE Yocto BSP documentation

* [BSP layers description](docs/layer-description.md)

* [Development process description](docs/development.md)

* [Release process description](https://docs.zarhus.com/development-process/standard-release-process/)

* [Instruction of setting the MAC address in U-Boot](docs/rte-mac-setup.md)

* [cpu command introduction and use case](docs/cpu.md)

# Usage

## Prerequisites

[kas](https://github.com/siemens/kas) installed. The preferred way is to use it
inside container, via the [kas-container] script.

### docker installation

* [docker installation](https://docs.docker.com/install/)

### kas-container installation

* [kas-container] installation:

```sh
wget -O ~/bin/kas-container https://raw.githubusercontent.com/siemens/kas/master/kas-container
chmod +x ~/bin/kas-container
```

## Build

```sh
mkdir rte-sdk
cd rte-sdk
git clone https://github.com/3mdeb/meta-rte.git
KAS_IMAGE_VERSION="4.2" kas-container build meta-rte/kas.yml
```

## Flash

There are two main ways to flash an image, etcher and bmaptool.

### etcher

The easiest way to flash image on all system platforms is to use `etcher`. It
is multi-platform application that is available for Linux, Windows or macOS.

* Download and install [etcher](https://etcher.io/).

* Insert the SD card to the SD card reader of your host PC.

* Run etcher.

* Click on `Select Image` and select built image. There is no need to unpack
the image first.

   The SD card reader should be picked automatically. If more than one readers
   are present, click on `Change` and select the one you want to use.

* When confirmed, click on the `Flash` button to start flashing procedure.

### bmaptool

If you prefer a command-line tool, then you should use bmaptool. To install it,
depending on the package manager your system uses, use one of the following
commands:

**for Ubuntu:**

```sh
sudo apt install bmap-tools
```

**for Fedora:**

```sh
sudo dnf install bmap-tools
```

you can then download the latest image from the
[releases](https://github.com/3mdeb/meta-rte/releases) page. You should
download both the `.wic.bmap` and `.wic.gz` files.

Once you have chosen the version you want and downloaded it (both files), you
can then flash it using bmaptool, like this:

```sh
bmaptool copy --bmap /path/to/example.wic.bmap /path/to/example.wic.gz /dev/sdX
```

in order to know what `/dev/sdX` you want to flash, its a good idea to use
the `lsblk` command, before and after plugging in your SD card. You should be
able to see the difference in the output of those two commands, and determine
what your SD card is called.

## Login to the system

```sh
Login: root
Password: meta-rte
```

## Update

To perform update on platform we will use
[SWUpdate](https://sbabic.github.io/swupdate/index.html) software already
installed on OS.

* Download latest available .swu update image from [releases
  page](https://github.com/3mdeb/meta-rte/releases).

* Provide anyhow downloaded .swu update image to device, for example using scp

  ```shell
  $ scp path/to/swu/file.swu root@RTE_IP:/path/to/deploy/file/
  ```

  >Note: you can check RTE_IP by running `ip a` command on RTE.

* Check active partition, run `findmnt /` command on RTE

  ```shell
  # findmnt /
  TARGET SOURCE         FSTYPE OPTIONS
  /      /dev/mmcblk0p2 ext4   rw,relatime
  ```

* Now run `swupdate` command, for flag `-e` we need to provide an inactive
  partition which in this case is `mmcblk0p3`. If `findmnt /` command would
  return `mmcblk0p3` then `mmcblk0p2` should be provided for `-e`. Flag `-i` is
  used to provide update image stored locally and `-v` will cause to print debug
  logs.

  ```shell
  # swupdate -e "rte,mmcblk0p3" -i /path/to/file.swu -v
  Swupdate v2020.04.0

  Licensed under GPLv2. See source distribution for detailed copyright notices.
  (...)
  Software updated successfully
  Please reboot the device to start the new software
  [INFO ] : SWUPDATE successful !
  [DEBUG] : SWUPDATE running :  [postupdate] : Running Post-update command
  ```

* After success reboot the board. Platform should start from different partition
  and updated system. This can be verified by running `cat /etc/os-release`.

  ```shell
  # cat /etc/os-release
  ID=rte
  NAME="RTE (Remote Test Environment Distro)"
  VERSION="0.7.4-rc1 (rocko)"
  VERSION_ID=0.7.4-rc1
  PRETTY_NAME="RTE (Remote Test Environment Distro) 0.7.4-rc1 (rocko)"
  ```

* From now, updated partition will be set as active, so every next reboot will
  cause to boot updated OS.

## OpenVPN

There is a possibility to use OpenVPN. The only thing you have to do is
copy the config file to the `openvpn` folder on `data` partition of
SD card with the name `rte.conf`.

## Serial

You can access the DUT's serial connection from the RS232/UART port at
`/dev/ttyS1`, for example, through Minicom:

```sh
# minicom -D /dev/ttyS1
```

All possible devices can be listed using following command:

```sh
# ls /dev/tty*
```

### Telnet

It is also possible to use telnet communication via RTE. On RTE there is serial
to network proxy service called `ser2net`. Its status can be seen by running a
command:

```sh
# systemctl -all | grep ser2net
  ser2net.service           loaded     active     running
```

Service configuration is placed here:

```sh
# cat /etc/ser2net.conf
13542:telnet:16000:/dev/ttyS1:115200 8DATABITS NONE 1STOPBIT
13541:telnet:16000:/dev/ttyUSB0:115200 8DATABITS NONE 1STOPBIT
```

The first value indicates the port for telnet communication, the path /dev/tty*
indicates the path to the device with which the connection is made.

Serial connection with the device can be established by command:

```sh
$ telnet <RTE_IP> <PORT>
```

for example:

```sh
$ telnet 192.168.4.170 13541
```

In this case, a serial connection will be made via RTE with IP 192.168.4.170
with device `/dev/ttyUSB0` using above configuration.

# Development workflow

## Individual MR

> Our [change log](CHANGELOG.md) mostly depends on the merge events and the
> merge request names are presented as introduced changes in this file. Be sure
to use `git add -p` and add only your version, so as not to change previous
release notes (incorrect links to previous pull requests).

* Commit changes

* Push on branch and create pull request

* Wait for review and merge
