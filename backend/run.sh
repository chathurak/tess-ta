#!/usr/bin/env bash

mvn clean verify package -Prelease -DskipTests

# Start TA
java -jar target/tess-ta-0.0.1-SNAPSHOT.jar