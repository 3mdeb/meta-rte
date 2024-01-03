# CPU example usage

This document describes an example use of the [cpu](https://github.com/u-root/cpu)
command.

## Introduction

The `cpu` command allows you to log in to a remote system (in this case RTE) and
see some of the files from your local system. This allows you to execute
programs installed on your local system in the remote environment. Another use
case is executing `minicom` on RTE and storing the logs on your host PC without
having to `scp` them and without worrying about RTE running out
of space. This use case is demonstrated in this document.

## Prerequisites

* RTE with system version `0.7.4-rc5` or later connected to your local network
* Some bootable platform connected to the RTE via serial
* The `cpu` command installed on your host PC

## Using cpu

This section describes how to use the `cpu` command to execute `minicom` on RTE
and store the logs on your host PC without them ever appearing on the RTE.

First you must generate an SSH key pair on your host.

```bash
$ ssh-keygen
Generating public/private rsa key pair
Enter file in which to save the key
```

Store it wherever you would like. In this example it is saved as `~/.ssh/key`.
Next, send the **public** key to the RTE. You can use `scp` to do it.

```bash
$ scp ~/.ssh/key.pub root@<rte_ip>:.ssh
```

The key will appear on RTE under `/home/root/.ssh/key.pub`.

Then you must access the RTE and run `cpud`, which will listen for
incoming connections. Make sure that the RTE is in your local network because
`cpu` utilizes SSH.

```bash
(rte)# cpud -pk /home/root/.ssh/key.pub
```

On your host PC navigate to your working directory and run `minicom` using
`cpu`.

```bash
$ cpu -key ~/.ssh/key -namespace /home <rte_ip> /usr/bin/minicom -D <dev>  -b <baud rate> -C capturefile
```

`minicom` should open. Boot the platform that is connected to the RTE.
You will see the output from the platform. When you're finished, close
`minicom`. The capture file will appear on the host instead of the RTE.
