#!/bin/bash

cd Quiz_app

echo "===== Starting MySQL ====="
sudo service mysql start

sleep 2

echo "===== Checking MySQL status ====="
sudo service mysql status

echo "===== Creating database if it doesn't exist ====="
mysql -u root -p"$MYSQL_ROOT_PASSWORD" -e "CREATE DATABASE IF NOT EXISTS quiz_app;"

echo "===== Database ready ====="

echo "===== Running Spring Boot App ====="
mvn spring-boot:run
