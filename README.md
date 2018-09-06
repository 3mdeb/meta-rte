meta-rte
========

# Usage

Refer to
[rte-oe-manifest README](https://gitlab.com/3mdeb/rte/rte-oe-manifest/blob/master/README.md)
for usage information.

# Development workflow

> Our [change log](CHANGELOG.md) mostly depends on the merge events and the
> merge request names are presented as introduced changes in this file.

* Commit changes

* Push on branch and create merge request

* Wait for review and merge

* Bump `DISTRO_VERSION` in the `conf/distro/rte.conf` file

* Pull the merge event into local repository

* Create the tag

* Update change log:

```.bash
generate-changelog.sh
```

* Commit and push change log

> We either can push directly to master, or via another PR. Neither solution
> seems perfect for me at the moment.
