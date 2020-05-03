FROM sumedhe/tesseract:latest

# Environment varialbes
ENV OCR_DIR       /ocr
ENV TESSTA_BE_DIR ${OCR_DIR}/backend
ENV TESSTA_FE_DIR ${OCR_DIR}/frontend
ENV TESSDATA_DIR  ${TESS_TA_DIR}/tessdata
ENV STORAGE_DIR   ${TESS_TA_DIR}/storage
ENV LOG_FILE      ${OCR_DIR}/log.txt

# Install Dependencies
RUN apt-get update && \
    apt-get install -y --fix-missing \
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

# Install Yarn
RUN curl -sL https://deb.nodesource.com/setup_12.x | bash - && \
    curl -sS https://dl.yarnpkg.com/debian/pubkey.gpg | apt-key add - && \
    echo "deb https://dl.yarnpkg.com/debian/ stable main" | tee /etc/apt/sources.list.d/yarn.list && \
    apt-get update && \
    apt-get install -y nodejs yarn
RUN npm install pm2 -g

# Setup fonts
RUN locale-gen en_US.UTF-8
ENV LANG       en_US.UTF-8
ENV LANGUAGE   en_US:en
ENV LC_ALL     en_US.UTF-8

# Build backend
ADD ./backend           ${TESSTA_BE_DIR}
RUN cd ${TESSTA_BE_DIR} && mvn clean verify package -Prelease -DskipTests

# Build frontend
ADD ./frontend          ${TESSTA_FE_DIR}
RUN cd ${TESSTA_FE_DIR} && yarn install

ADD ./start_services.sh ${OCR_DIR}

# Yarn expose port
ENV PORT 80

# Create log file
RUN touch ${LOG_FILE}

WORKDIR ${OCR_DIR}

EXPOSE 80
EXPOSE 8080

CMD sh start_services.sh
