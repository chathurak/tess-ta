ARG RELEASE=19.10

FROM ubuntu:${RELEASE} AS dependencies

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


# SSH for diagnostic
RUN apt-get update && apt-get install -y --allow-downgrades --allow-remove-essential --allow-change-held-packages openssh-server
RUN mkdir /var/run/sshd
RUN echo 'root:troubl3tim3' | chpasswd
RUN sed -i 's/PermitRootLogin prohibit-password/PermitRootLogin yes/' /etc/ssh/sshd_config
# SSH login fix. Otherwise user is kicked off after login
RUN sed 's@session\s*required\s*pam_loginuid.so@session optional pam_loginuid.so@g' -i /etc/pam.d/sshd
ENV NOTVISIBLE "in users profile"
RUN echo "export VISIBLE=now" >> /etc/profile

EXPOSE 22
CMD ["/usr/sbin/sshd", "-D"]

# Directories
ENV SCRIPTS_DIR /home/scripts
ENV PKG_DIR /home/pkg
ENV BASE_DIR /home/workspace
ENV LEP_REPO_URL https://github.com/DanBloomberg/leptonica.git
ENV LEP_SRC_DIR ${BASE_DIR}/leptonica
ENV TES_REPO_URL https://github.com/tesseract-ocr/tesseract.git
ENV TES_SRC_DIR ${BASE_DIR}/tesseract
ENV TESSDATA_PREFIX /usr/local/share/tessdata

RUN mkdir ${SCRIPTS_DIR}
RUN mkdir ${PKG_DIR}
RUN mkdir ${BASE_DIR}
RUN mkdir ${TESSDATA_PREFIX}

COPY ./scripts/* ${SCRIPTS_DIR}/
RUN chmod +x ${SCRIPTS_DIR}/*
RUN ${SCRIPTS_DIR}/repos_clone.sh
RUN ${SCRIPTS_DIR}/tessdata_download.sh

WORKDIR /home

# Update repos
RUN /home/scripts/repos_update.sh

# Build leptonica
RUN /home/scripts/compile_leptonica.sh

# Build tesseract
RUN /home/scripts/compile_tesseract.sh

# Build packages
#RUN /home/scripts/build_deb_pkg.sh

# Environment varialbes
ENV OCR_DIR /ocr
ENV TESS_TA_DIR ${OCR_DIR}/tess-ta
ENV TESSDATA_DIR ${TESS_TA_DIR}/tessdata
ENV STORAGE_DIR ${TESS_TA_DIR}/storage
ENV LOG_FILE ${OCR_DIR}/log.txt

# Install Dependencies
RUN apt-get update && apt-get install -y --fix-missing \
    curl \
    wget \
    unzip \
    git \
    apt-transport-https \
    openjdk-13-jre \
	openjdk-13-jdk \
	maven \
	vim \
	psmisc \
	htop \
	tree \
	locales\
	imagemagick

WORKDIR ${OCR_DIR}/build

COPY pom.xml .

# Download Maven dependancies
RUN mvn dependency:go-offline

FROM dependencies AS intermediate

# Create log
RUN touch ${LOG_FILE}

# Copy tess-ta repo
ADD ./database/db.sql ${TESS_TA_DIR}/database/db.sql
ADD ./src/main/app/public ${TESS_TA_DIR}/src/main/app/public
ADD ./src/main/app/src ${TESS_TA_DIR}/src/main/app/src
ADD ./src/main/app/package.json ${TESS_TA_DIR}/src/main/app/package.json
ADD ./src/main/app/yarn.lock ${TESS_TA_DIR}/src/main/app/yarn.lock
ADD ./src/main/java ${TESS_TA_DIR}/src/main/java
ADD ./src/main/resources ${TESS_TA_DIR}/src/main/resources
ADD ./src/test ${TESS_TA_DIR}/src/test
ADD ./pom.xml ${TESS_TA_DIR}/pom.xml
ADD ./start-services.sh ${TESS_TA_DIR}/start-services.sh

# Change working directory
WORKDIR ${TESS_TA_DIR}

# Build tess-ta
RUN mvn clean verify package -Prelease -DskipTests

# Setup fonts
RUN locale-gen en_US.UTF-8
ENV LANG en_US.UTF-8
ENV LANGUAGE en_US:en
ENV LC_ALL en_US.UTF-8

# Export ports
EXPOSE 4000

CMD sh start-services.sh