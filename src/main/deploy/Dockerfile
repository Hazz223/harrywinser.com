FROM openjdk:8u151-jre-alpine

ADD app.jar /usr/src/app.jar
ADD application.properties /usr/src/application.properties

WORKDIR /usr/src/

EXPOSE 3000

CMD ["java", "-jar", "app.jar"]