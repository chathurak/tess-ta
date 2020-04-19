FROM sumedhe/tesseract:latest

# Environment varialbes
ENV OCR_DIR       /ocr
ENV TESSTA_BE_DIR ${OCR_DIR}/backend
ENV TESSTA_FE_DIR ${_DIR}/frontend
ENV TESSDATA_DIR  ${TESS_TA_DIR}/tessdata
ENV STORAGE_DIR   ${TESS_TA_DIR}/storage
ENV LOG_FILE      ${OCR_DIR}/log.txt

# Install Dependencies
RUN apt-get update && apt-get install -y --fix-missing \
    curl \
    wget \
    unzip \
    git \
    apt-transport-https \
    openjdk-11-jre \
    openjdk-11-jdk \
    maven \
    vim \
    psmisc \
    htop \
    tree \
    locales\
    imagemagick

WORKDIR ${OCR_DIR}/build

# Create log
RUN touch ${LOG_FILE}

# Copy tess-ta backend repo
ADD ./database/db.sql           ${TESS_TA_DIR}/database/db.sql
ADD ./backend/src               ${TESSTA_BE_DIR}/src
ADD ./backend/pom.xml           ${TESSTA_BE_DIR}/pom.xml
# ADD ./backend/start-services.sh ${TESSTA_BE_DIR}/start-services.sh

# Copy tess-ta frontend repo
ADD ./frontend/src          ${TESSTA_FE_DIR}/src
ADD ./frontend/public       ${TESSTA_FE_DIR}/public
ADD ./frontend/package.json ${TESSTA_FE_DIR}/package.json
ADD ./frontend/yarn.lock    ${TESSTA_FE_DIR}/yarn.lock

# Change working directory
WORKDIR ${OCR_DIR}

# Build tess-ta
RUN cd ${TESSTA_BE_DIR} && mvn clean verify package -Prelease -DskipTests

# Setup fonts
RUN locale-gen en_US.UTF-8
ENV LANG       en_US.UTF-8
ENV LANGUAGE   en_US:en
ENV LC_ALL     en_US.UTF-8

ADD ./backend/start-services.sh ${TESSTA_BE_DIR}/start-services.sh

# Export ports
EXPOSE 4000

WORKDIR ${TESSTA_BE_DIR}
CMD sh start-services.sh

