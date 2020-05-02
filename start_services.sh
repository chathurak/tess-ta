#!/bin/bash

################################################
# Script to start services in docker container #
################################################

# Start frontend
cd ${TESSTA_FE_DIR} && pm2 start yarn --interpreter bash --name api -- start

sleep 40

# Start backend
cd ${TESSTA_BE_DIR} && java -jar target/tess-ta-0.0.1-SNAPSHOT.jar > ${LOG_FILE} 2> ${LOG_FILE} &

tail -f ${LOG_FILE}

