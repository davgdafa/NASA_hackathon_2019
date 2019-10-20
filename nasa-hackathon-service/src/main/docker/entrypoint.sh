#!/bin/bash

set -e

echo "Entrypoint"

echo java $JAVA_OPTS -cp /app:/app/lib/* -jar app.jar
java $JAVA_OPTS -cp /app:/app/lib/* -jar app.jar
