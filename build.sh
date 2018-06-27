#!/usr/bin/env bash

WAR=cocktailize-1.0-SNAPSHOT.war

echo -n "Maven build..."
mvn -q -DskipTests clean package
echo "Done!"

echo "Docker build..."
docker build -t loick111/cocktailize .
echo "Done!"
