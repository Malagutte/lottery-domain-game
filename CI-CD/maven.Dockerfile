FROM openjdk:17.0.2-slim

USER root

WORKDIR /app

RUN \

#ADD curl command
apt-get update && \
apt-get install curl -y && \

echo "Downlaoding maven" && \
mkdir -p /usr/share/maven /usr/share/maven/ref && \
curl -o /tmp/apache-maven.tar.gz https://dlcdn.apache.org/maven/maven-3/3.8.6/binaries/apache-maven-3.8.6-bin.tar.gz && \

echo "Unziping maven" && \
tar -xzf /tmp/apache-maven.tar.gz -C /usr/share/maven --strip-components=1 && \

echo "Cleaning and setting links" && \
rm -f /tmp/apache-maven.tar.gz  && \
ln -s /usr/share/maven/bin/mvn /usr/bin/mvn


ENV MAVEN_HOME /usr/share/maven
ENV MAVEN_CONFIG "$USER_HOME_DIR/.m2"