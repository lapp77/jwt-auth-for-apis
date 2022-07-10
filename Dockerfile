# builder
FROM gradle:6.8.3-jdk11 AS build
COPY . /home/gradle
WORKDIR /home/gradle
RUN gradle --no-daemon build --stacktrace

# app
FROM adoptopenjdk/openjdk11:alpine

EXPOSE 8080

RUN mkdir -p /app/

COPY --from=build /home/gradle/build/libs/simplysend-0.0.1-SNAPSHOT.jar  /app/app.jar
COPY --from=build /home/gradle/src/main/resources/application.properties /app/application.properties

# configure wait-for-it for database
ADD https://github.com/ufoscout/docker-compose-wait/releases/download/2.7.3/wait /wait
RUN chmod +x /wait

CMD /wait && java -jar /app/app.jar
