FROM gradle:4.2.1-jdk8-alpine

  export DEBIAN_FRONTEND=noninteractive && \
  sed -i 's/# \(.*multiverse$\)/\1/g' /etc/apt/sources.list && \
  apt-get update && \
  apt-get -y upgrade && \
  apt-get install -y vim wget curl git gradle

RUN echo "export JAVA_HOME=`/usr/libexec/java_home`" >> ~/.bashrc

ENV JAVA_HOME=`/usr/libexec/java_home`
ENV PATH $JAVA_HOME/bin:$PATH

VOLUME /home

RUN mkdir -p /home
WORKDIR /home

RUN ./gradlew init


ADD ./build.gradle /home

USER root

RUN chown -R gradle /home

RUN ./gradlew clean test
