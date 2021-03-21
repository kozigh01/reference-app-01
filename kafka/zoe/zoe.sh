#!/bin/bash
# fix broken packages in linux: https://www.maketecheasier.com/fix-broken-packages-ubuntu/

apt update
apt upgrade
apt --yes install libasound2
apt --yes --fix-broken install

# ZOE_VERSION='0.26.0'  # change it to the suitable version
# curl -L "https://github.com/adevinta/zoe/releases/download/v${ZOE_VERSION}/zoe_${ZOE_VERSION}-1_amd64.deb" -o /tmp/zoe.deb
curl -L "https://github.com/adevinta/zoe/releases/download/v0.26.1/zoe_0.26.1-1_amd64.deb" -o /tmp/zoe.deb
sudo dpkg -i /tmp/zoe.deb
PATH=$PATH:/opt/zoe/bin