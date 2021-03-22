#!/bin/bash
# fix broken packages in linux: https://www.maketecheasier.com/fix-broken-packages-ubuntu/

# add dependencies that will be needed by the zoe.deb installation
apt update
apt upgrade
apt --yes install libasound2
apt --yes --fix-broken install

# Zoe installation: https://adevinta.github.io/zoe/install/package/
# ZOE_VERSION='0.26.0'  # change it to the suitable version
# curl -L "https://github.com/adevinta/zoe/releases/download/v${ZOE_VERSION}/zoe_${ZOE_VERSION}-1_amd64.deb" -o /tmp/zoe.deb
curl -L "https://github.com/adevinta/zoe/releases/download/v0.26.1/zoe_0.26.1-1_amd64.deb" -o /tmp/zoe.deb
sudo dpkg -i /tmp/zoe.deb
PATH=$PATH:/opt/zoe/bin

# configuration: https://adevinta.github.io/zoe/configuration/init/
# change ~/.zoe/config/default.yml "bootstrap.servers" to ""
zoe config init --from git --url 'https://github.com/adevinta/zoe.git' --dir docs/guides/simple/config



# https://www.katacoda.com/wlezzar/courses/zoe
