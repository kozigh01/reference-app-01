#!/bin/bash
# fix broken packages in linux: https://www.maketecheasier.com/fix-broken-packages-ubuntu/

# add dependencies that will be needed by the zoe.deb installation
apt --yes update
apt --yes upgrade
apt --yes install libasound2
apt --yes --fix-broken install
apt install jq

# Zoe installation: https://adevinta.github.io/zoe/install/package/
# ZOE_VERSION='0.26.0'  # change it to the suitable version
# curl -L "https://github.com/adevinta/zoe/releases/download/v${ZOE_VERSION}/zoe_${ZOE_VERSION}-1_amd64.deb" -o /tmp/zoe.deb
curl -L "https://github.com/adevinta/zoe/releases/download/v0.26.1/zoe_0.26.1-1_amd64.deb" -o /tmp/zoe.deb
sudo dpkg -i /tmp/zoe.deb
PATH=$PATH:/opt/zoe/bin

# configuration: https://adevinta.github.io/zoe/configuration/init/
# change ~/.zoe/config/default.yml "bootstrap.servers" to "brokern"
# zoe config init --from git --url 'https://github.com/adevinta/zoe.git' --dir docs/guides/simple/config
zoe config init
cp ./default.yml ~/.zoe/config/default.yml

# https://www.katacoda.com/wlezzar/courses/zoe/zoe-basics
zoe config clusters list | jq
zoe -o table config clusters list
zoe --cluster default topics list | jq
zoe --cluster default -o table topics list
zoe topics create cat-facts --partitions 5
zoe topics list | jq



# https://www.katacoda.com/wlezzar/courses/zoe
