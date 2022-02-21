FROM alpine:3.7

MAINTAINER chuang.chen <chuang.chen@supernovacompanies.biz>

VOLUME ["/var/www/webapp"]

COPY target/sn-output-service.jar /var/www/webapp/application.jar

