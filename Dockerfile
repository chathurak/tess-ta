FROM ubuntu:19.04 AS dependencies

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
    openjdk-12-jre \
	openjdk-12-jdk \
	maven \
    redis-server \
	vim \
	psmisc \
	htop \
	tree \
	locales \
	mysql-client \
	mongodb \
	docker \
	docker.io \
	docker-compose

WORKDIR ${OCR_DIR}/build

COPY pom.xml .

# Download Maven dependancies
RUN mvn dependency:go-offline

FROM dependencies AS intermediate

# Create log
RUN touch ${LOG_FILE}

# Clone tess-ta repo
RUN git clone https://github.com/sinhala-ocr/tess-ta.git ${TESS_TA_DIR}

# Change working directory
WORKDIR ${TESS_TA_DIR}

# Build tess-ta
RUN mvn clean verify package -Prelease -DskipTests

# Setup fonts
RUN locale-gen en_US.UTF-8
ENV LANG en_US.UTF-8
ENV LANGUAGE en_US:en
ENV LC_ALL en_US.UTF-8

# Add tesseract command
RUN bash -c 'echo -e "#!/bin/bash\ndocker exec ocr-tesseract-daemon tesseract" > /usr/bin/tesseract && chmod +x /usr/bin/tesseract'

# Add text2iamge command
RUN bash -c 'echo -e "#!/bin/bash\ndocker exec ocr-tesseract-daemon text2iamge" > /usr/bin/text2iamge && chmod +x /usr/bin/text2iamge'

# Export ports
EXPOSE 4000

CMD sh start-services.sh