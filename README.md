meta-rte
========

# Usage

## Prerequisites

[kas](https://github.com/siemens/kas) installed. The preferred way is to use it
inside container, via the [kas-docker] script.

### docker installation

* [docker installation](https://docs.docker.com/install/)

### kas-docker installation

* [kas-docker] installation:

```
wget -O ~/bin/kas-docker https://raw.githubusercontent.com/siemens/kas/master/kas-docker
chmod +x ~/bin/kas-docker
```

## Build

```
mkdir rte-sdk
cd rte-sdk
git clone https://github.com/3mdeb/meta-rte.git
kas-docker build meta-rte/kas.yml
```

## Flash

The easiest way to flash image on all system platforms is to use `etcher`. It
is multi-platform application that is available for Linux, Windows or macOS.

- Download and install [etcher](https://etcher.io/).

- Insert the SD card to the SD card reader of your host PC.

- Run etcher.

- Click on `Select Image` and select built image. There is no need to unpack
the image first.

   The SD card reader should be picked automatically. If more than one readers
   are present, click on `Change` and select the one you want to use.

- When confirmed, click on the `Flash` button to start flashing procedure.

## Login to the system

```
Login: root
Password: meta-rte
```

## OpenVPN

There is a possibility to use OpenVPN. The only thing you have to do is
copy the config file to the `openvpn` folder on `data` partition of
SD card with the name `rte.conf`.

## Serial

You can access the DUT's serial connection from the RS232/UART port at
`/dev/ttyS1`, for example, through Minicom:

```
# minicom -D /dev/ttyS1
```

All possible devices can be listed using following command:

```
# ls /dev/tty*
```

### Telnet

It is also possible to use telnet communication via RTE. On RTE there is serial
to network proxy service called `ser2net`. Its status can be seen by running a
command:

```
# systemctl -all | grep ser2net
  ser2net.service           loaded     active     running
```

Service configuration is placed here:

```
# cat /etc/ser2net.conf 
13542:telnet:16000:/dev/ttyS1:115200 8DATABITS NONE 1STOPBIT
13541:telnet:16000:/dev/ttyUSB0:115200 8DATABITS NONE 1STOPBIT
```

The first value indicates the port for telnet communication, the path /dev/tty*
indicates the path to the device with which the connection is made.

Serial connection with the device can be established by command:

```
$ telnet <RTE_IP> <PORT>
```

for example:

```
$ telnet 192.168.4.170 13541
```

In this case, a serial connection will be made via RTE with IP 192.168.4.170
with device `/dev/ttyUSB0` using above configuration.

# Development workflow

## Individual MR

> Our [change log](CHANGELOG.md) mostly depends on the merge events and the
> merge request names are presented as introduced changes in this file.

* Commit changes

* Push on branch and create pull request

* Wait for review and merge

# Meta-RTE Yocto BSP documentation

* [BSP layers description](docs/layer-description.md)

* [Development process description](docs/development.md)

* [Release process description](docs/release.md)

[kas-docker]: https://github.com/siemens/kas/blob/master/kas-docker
