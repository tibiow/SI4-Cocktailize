#!/bin/sh

mkdir -p ./WEB-INF/classes/
touch ./WEB-INF/classes/hibernate.properties
echo "hibernate.connection.url=jdbc:mysql://$DB_HOST:$DB_PORT/$DB_NAME?useSSL=$DB_SSL" >> ./WEB-INF/classes/hibernate.properties
echo "hibernate.connection.username=$DB_USERNAME" >> ./WEB-INF/classes/hibernate.properties
echo "hibernate.connection.password=$DB_PASSWORD" >> ./WEB-INF/classes/hibernate.properties
echo "hibernate.hbm2ddl.auto=create" >> ./WEB-INF/classes/hibernate.properties

jar uvf ./webapps/cocktailize.war ./WEB-INF/classes/hibernate.properties

catalina.sh run