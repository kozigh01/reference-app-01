# See here for image contents: https://github.com/microsoft/vscode-dev-containers/tree/v0.163.1/containers/ubuntu/.devcontainer/base.Dockerfile

# [Choice] Ubuntu version: bionic, focal
ARG VARIANT="focal"
FROM mcr.microsoft.com/vscode/devcontainers/base:0-${VARIANT}

WORKDIR /workspace

# [Optional] Uncomment this section to install additional OS packages.
# RUN apt-get update && export DEBIAN_FRONTEND=noninteractive \
#     && apt-get -y install --no-install-recommends <your-package-list-here>

RUN apt --yes update \
    && apt --yes upgrade \
    && apt --yes install default-jre

    # RUN sudo adduser kafka \
    # && sudo adduser kafka \
    # && sudo adduser kafka sudo \
    # && su -l kafka

RUN mkdir ~/Downloads \
    && curl "https://downloads.apache.org/kafka/2.6.1/kafka_2.13-2.6.1.tgz" -o ~/Downloads/kafka.tgz \
    && mkdir ~/kafka && cd ~/kafka \
    && tar -xvzf ~/Downloads/kafka.tgz --strip 1
    
ENV PATH "/root/kafka/bin:$PATH"