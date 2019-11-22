#!/usr/bin/env bash

# Install Dependencies
sudo apt-get update && sudo apt-get install -y --fix-missing \
    curl \
    wget \
    unzip \
    apt-transport-https \
	vim \
	psmisc \
	htop \
	tree \
	locales \
	git \
	openjdk-13-jre \
	openjdk-13-jdk \
    maven \
	mysql-server \
    redis-server
    # TODO : Add MongoDb

# Mysql
sudo systemctl enable mysql.service
sudo systemctl start mysql.service
sudo systemctl status mysql.service

# MySQL - Init db
mysql -u root < ./database/db.sql

# Redis
sudo systemctl enable redis-server.service
sudo systemctl start redis-server.service
sudo systemctl status redis-server.service

######################################## Tesseract ########################################

# Dependencies
sudo apt-get update && sudo apt-get install -y \
	autoconf \
	autoconf-archive \
	automake \
	build-essential \
	checkinstall \
	cmake \
	g++ \
	git \
	libcairo2-dev \
	libicu-dev \
	libjpeg-dev \
	libpango1.0-dev \
	libgif-dev \
	libwebp-dev \
	libopenjp2-7-dev \
	libpng-dev \
	libtiff-dev \
	libtool \
	pkg-config \
	wget \
	xzgv \
	zlib1g-dev

# Directories
export SCRIPTS_DIR=/home/scripts
export PKG_DIR=/home/pkg
export BASE_DIR=/home/workspace
export LEP_REPO_URL=https://github.com/DanBloomberg/leptonica.git
export LEP_SRC_DIR=${BASE_DIR}/leptonica
export TES_REPO_URL=https://github.com/tesseract-ocr/tesseract.git
export TES_SRC_DIR=${BASE_DIR}/tesseract
export TESSDATA_PREFIX=/usr/local/share/tessdata

# Create directories
mkdir ${SCRIPTS_DIR}
mkdir ${PKG_DIR}
mkdir ${BASE_DIR}
mkdir ${TESSDATA_PREFIX}

# Downloading source code
# Leptonica
# RUN git ls-remote ${LEP_REPO_URL} HEAD
git clone ${LEP_REPO_URL} ${LEP_SRC_DIR}
# Tesseract
# RUN git ls-remote ${TES_REPO_URL} HEAD
git clone ${TES_REPO_URL} ${TES_SRC_DIR}

# Compilation Leptonica
cd ${LEP_SRC_DIR}
autoreconf -vi && ./autogen.sh && ./configure
make && make install

# Compilation Tesseract
cd ${TES_SRC_DIR}
./autogen.sh && ./configure
LDFLAGS="-L/usr/local/lib" CFLAGS="-I/usr/local/include" make
make
make install && ldconfig
make training && make training-install

# osd	Orientation and script detection
wget -O ${TESSDATA_PREFIX}/osd.traineddata https://github.com/tesseract-ocr/tessdata/raw/3.04.00/osd.traineddata
# equ	Math / equation detection
wget -O ${TESSDATA_PREFIX}/equ.traineddata https://github.com/tesseract-ocr/tessdata/raw/3.04.00/equ.traineddata
# eng English
wget -O ${TESSDATA_PREFIX}/eng.traineddata https://github.com/tesseract-ocr/tessdata/raw/4.00/eng.traineddata
# other languages: https://github.com/tesseract-ocr/tesseract/wiki/Data-Files