meta-rte
========

# Usage

Refer to
[rte-oe-manifest README](https://gitlab.com/3mdeb/rte/rte-oe-manifest/blob/master/README.md)
for usage information.

# Development workflow

* commit changes

* push on branch and create merge request

* wait for review and merge

* pull the merge event into local repository

* create the tag

* update change log:

```.bash
generate-changelog.sh
```

> Our [change log](CHANGELOG.md) mostly depends on the merge events and the
> merge request names are presented as introduced changes in this file.
