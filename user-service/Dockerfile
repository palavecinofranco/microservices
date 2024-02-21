FROM openjdk:17-jdk-alpine as builder
ARG MSVC_NAME=user-service

WORKDIR /app/user-msvc

COPY ./pom.xml /app
COPY ./$MSVC_NAME/.mvn ./.mvn
COPY ./$MSVC_NAME/mvnw .
COPY ./$MSVC_NAME/pom.xml .

RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/

COPY ./$MSVC_NAME/src ./src

RUN ./mvnw clean package -DskipTests

FROM openjdk:17-jdk-alpine

WORKDIR /app

RUN mkdir ./logs

COPY --from=builder /app/user-msvc/target/user-service-0.0.1-SNAPSHOT.jar .

ENV PORT 8001

EXPOSE $PORT

CMD ["java", "-jar", "user-service-0.0.1-SNAPSHOT.jar"]