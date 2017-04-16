FROM hazz22/pi-openjre

ADD app.jar /usr/src/app.jar
ADD application.properties /usr/src/application.properties

WORKDIR /usr/src/

RUN sh -c 'touch app.jar'

EXPOSE 9000

CMD ["java", "-jar", "app.jar"]
