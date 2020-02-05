FROM openjdk:11

ADD target/Household-Web-1.0-SNAPSHOT.jar /opt/application/Household-Web-1.0-SNAPSHOT.jar
RUN chown -R 1000:root /opt/application &&\
    chmod -R 775 /opt/application

USER 1000
WORKDIR /opt/application
EXPOSE 8181

ENTRYPOINT ["java",\
    "-Djava.security.egd=file:/dev/./urandom",\
    "-jar",\
    "/opt/application/Household-Web-1.0-SNAPSHOT.jar"]