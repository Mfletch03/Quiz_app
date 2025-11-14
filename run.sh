#!/bin/bash

cd Quiz_app

sleep 1

echo "===== Starting MySQL ====="
sudo service mysql start

sleep 2

echo "===== Checking MySQL status ====="
sudo service mysql status

echo "===== Database ready ====="

echo "===== Running Spring Boot App ====="
mvn clean install

mvn spring-boot:run
