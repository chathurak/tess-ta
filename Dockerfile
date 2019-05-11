FROM ubuntu:16.04

# Environment varialbes
ENV OCR_DIR /ocr
ENV TESS_SERVER_DIR ${OCR_DIR}/tesseract-server
ENV TESS_TA_DIR ${OCR_DIR}/tesseract-ta
ENV TESSERACT_BUILD /tesseract
ENV LOG_FILE ${OCR_DIR}/storage/log.txt

# TESSERACT: Install dependencies
RUN apt-get update && apt-get install -y \
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

# TESSERACT: Clone repos
RUN mkdir ${TESSERACT_BUILD} && \
	git clone https://github.com/DanBloomberg/leptonica.git ${TESSERACT_BUILD}/leptonica && \
	git clone https://github.com/sinhala-ocr/tesseract.git ${TESSERACT_BUILD}/tesseract

# TESSERACT: Build and Install leptonica
RUN cd ${TESSERACT_BUILD}/leptonica && \
    autoreconf -vi && ./autogen.sh && ./configure && \
    make && make install

# TESSERACT: Build and Install tesseract
RUN cd ${TESSERACT_BUILD}/tesseract && \
    ./autogen.sh && ./configure && \
    LDFLAGS="-L/usr/local/lib" CFLAGS="-I/usr/local/include" make && \
    make && \
    make install && ldconfig && \
    make training && make training-install

# SINHALA_OCR: Install Dependencies
RUN apt-get update && apt-get install -y --fix-missing \
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
	mysql-client
	
RUN curl -sL https://deb.nodesource.com/setup_10.x -o nodesource_setup.sh && \
	bash nodesource_setup.sh && \
	apt install -y nodejs

# SINHALA_OCR: Clone Repos
RUN git clone https://github.com/sinhala-ocr/tesseract-ta.git ${TESS_TA_DIR} && \
	git clone https://github.com/sinhala-ocr/tesseract-server.git ${TESS_SERVER_DIR}

# SINHALA_OCR: Build tesseract-server
RUN npm install -g @angular/cli && \
	cd ${TESS_SERVER_DIR} && npm install && npm audit fix --force

# SINHALA_OCR: Build tesseract-ta
RUN cd ${TESS_TA_DIR} && mvn compile package

# SINHALA_OCR: Copy files
COPY ./samples ${OCR_DIR}/samples
COPY ./tessdata ${OCR_DIR}/tessdata
COPY ./container_files/* ${OCR_DIR}/ 
COPY ./cred/TessTA-credentials.json ${TESS_TA_DIR}/src/main/resources

# Setup fonts
RUN locale-gen en_US.UTF-8
ENV LANG en_US.UTF-8
ENV LANGUAGE en_US:en
ENV LC_ALL en_US.UTF-8

# Export ports
EXPOSE 4001
EXPOSE 4002

# Working directory
WORKDIR ${OCR_DIR}

# CMD tail -f /dev/null
CMD sh /ocr/start_services.sh

