#!/usr/bin/bash

apt update
apt upgrade

sudo adduser kafka
sudo adduser kafka
sudo adduser kafka sudo
su -l kafka
mkdir ~/Downloads
curl "https://downloads.apache.org/kafka/2.6.1/kafka_2.13-2.6.1.tgz" -o ~/Downloads/kafka.tgz
mkdir ~/kafka && cd ~/kafka
tar -xvzf ~/Downloads/kafka.tgz --strip 1
export PATH="/home/kafka/kafka/bin:$PATH"