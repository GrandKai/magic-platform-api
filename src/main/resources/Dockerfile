FROM tomcat:8.5.43-jdk8-openjdk-slim
MAINTAINER "grandkai GrandKai@aliyun.com"
WORKDIR /user/local/tomcat
RUN echo "hello Dockerfile!" \
    > /usr/local/file.txt
COPY application.yml /etc/springboot/magic-platform-api/src/resources