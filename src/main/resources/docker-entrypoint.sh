#!/bin/bash
set -e

# Start MySQL service
service mysql start

# Initialize MySQL database if needed
if [ -f /docker-entrypoint-initdb.d/init.sql ]; then
    mysql -u root -p${password} < /docker-entrypoint-initdb.d/init.sql
fi

# Start the Java application
exec java -jar /src/app.jar
