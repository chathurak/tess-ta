#!/bin/bash

# Install Dependencies
apt-get update && apt-get install -y \
    git \
	autoconf \
	autoconf-archive \
	automake \
	build-essential \
	checkinstall \
	cmake \
	g++ \
	git \
	libcairo2-dev \
	libcairo2-dev \
	libicu-dev \
	libicu-dev \
	libjpeg8-dev \
	libjpeg8-dev \
	libpango1.0-dev \
	libpango1.0-dev \
	libpng12-dev \
	libpng12-dev \
	libtiff5-dev \
	libtiff5-dev \
	libtool \
	pkg-config \
	wget \
	xzgv \
	zlib1g-dev

TESSERACT_BUILD=/tesseract
mkdir ${TESSERACT_BUILD} && \
	git clone https://github.com/DanBloomberg/leptonica.git ${TESSERACT_BUILD}/leptonica && \
	git clone https://github.com/sinhala-ocr/tesseract.git ${TESSERACT_BUILD}/tesseract

cd ${TESSERACT_BUILD}/leptonica && \
    autoreconf -vi && ./autogen.sh && ./configure && \
    make && make install

cd ${TESSERACT_BUILD}/tesseract && \
    ./autogen.sh && ./configure && \
    LDFLAGS="-L/usr/local/lib" CFLAGS="-I/usr/local/include" make && \
    make && \
    make install && ldconfig && \
    make training && make training-install

apt-get update && apt-get install -y --fix-missing \
    curl \
    wget \
    unzip \
    git \
    apt-transport-https \
    openjdk-8-jre \
	openjdk-8-jdk \
	maven \
    redis-server \
	vim \
	psmisc \
	htop \
	tree \
	locales \
	mysql-client \
    software-properties-common \
    mysql-server \
    mysql-client 

apt-get update && apt-get install -y oracle-java12-installer


