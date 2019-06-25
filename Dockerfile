FROM ubuntu:19.04

# Environment varialbes
ENV OCR_DIR /ocr
ENV TESS_TA_DIR ${OCR_DIR}/tess-ta
ENV TESSDATA_DIR ${TESS_TA_DIR}/tessdata
ENV STORAGE_DIR ${TESS_TA_DIR}/storage
ENV LOG_FILE ${OCR_DIR}/log.txt

# Create directories
RUN mkdir ${OCR_DIR}

# SINHALA_OCR: Install Dependencies
RUN apt-get update && apt-get install -y --fix-missing \
    curl \
    wget \
    unzip \
    git \
    apt-transport-https \
    openjdk-12-jre \
	openjdk-12-jdk \
	maven \
    redis-server \
	vim \
	psmisc \
	htop \
	tree \
	locales \
	mysql-client

# Clone tess-ta repo
RUN git clone https://github.com/sinhala-ocr/tess-ta.git ${TESS_TA_DIR}

# Build tess-ta
RUN cd ${TESS_TA_DIR} && \
    mvn clean verify package -Prelease -DskipTests

# Setup fonts
RUN locale-gen en_US.UTF-8
ENV LANG en_US.UTF-8
ENV LANGUAGE en_US:en
ENV LC_ALL en_US.UTF-8

# Export ports
EXPOSE 4000

# Working directory
WORKDIR ${TESS_TA_DIR}

CMD sh start-services.sh