
FROM adoptopenjdk/openjdk11:alpine-jre

ARG JAR_FILE=target/myblog-1.0.0.jar

WORKDIR /usr/blog

COPY ${JAR_FILE} myblog-1.0.0.jar

ENTRYPOINT ["java","-jar","-Dspring.profiles.active=prod","myblog-1.0.0.jar"]