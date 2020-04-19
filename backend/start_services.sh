#!/bin/bash

sleep 20

# Start TA
java -jar target/tess-ta-0.0.1-SNAPSHOT.jar > ${LOG_FILE} 2> ${LOG_FILE} &

tail -f ${LOG_FILE}

