# Start services
service redis-server start

sleep 15

# Start TA
java -jar target/tess-ta-0.0.1-SNAPSHOT.jar > ${LOG_FILE} 2> ${LOG_FILE} &

tail -f ${LOG_FILE}