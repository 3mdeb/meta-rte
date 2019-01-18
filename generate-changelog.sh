#!/bin/bash

docker run -it -v $(pwd):$(pwd) -w $(pwd) macpijan/auto-changelog \
    --template keepachangelog --output CHANGELOG.md --unreleased --commit-limit=0
