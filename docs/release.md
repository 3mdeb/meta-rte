# Release process of the RTE Yocto BSP

## Repositories list

List of repositories to be released as a part of the `Yocto RTE BSP` release
process:

* [meta-rte](https://gitlab.com/3mdeb/rte/meta-rte)

## General release process

* Pull the merge events into local repository

* Bump `DISTRO_VERSION` in the `conf/distro/rte.conf` file

* Create the tag

* Update change log:

> [auto-changelog](https://github.com/CookPete/auto-changelog) tool is used
> there

```.bash
generate-changelog.sh
```

* Commit and push change log

> We either can push directly to master, or via another PR. Neither solution
> seems perfect for me at the moment.

## Versioning scheme

`meta-rte` is versioned using [semantic versioning](http://semver.org/) scheme
with the following clarifications and additions:

* `PATCH` version is incremented with each released `HOTFIX`
* `PATCH` version is zeroed with the increment of `MINOR` or `MAJOR` versions
* `MINOR` version is incremented with every `FEATURE` release
 (may include more then one feature) unless MAJOR version is incremented
* `MINOR` version is zeroed with the increment  of `MAJOR` version
* `MAJOR` version is incremented according to the project road map.
 Project road map should define at which point in time or with which feature
 set next `MAJOR` release is ready.

Depending on the need of a particular software project, a `pre-release`
(`release candidate`) version may be released. The version format must follow
the [pre-release semver versioning scheme](https://semver.org/#spec-item-9).
Generally, in `RTE` software project we shall use the following scheme for
`release candidates`:

```
MAJOR.MINOR.PATCH-rc.RC_ID
```

where the `RC_ID` starts with `1`.
