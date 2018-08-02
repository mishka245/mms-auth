FROM java:8

VOLUME /tmp

EXPOSE 8080

ADD /target/mms-auth-service-0.0.1-SNAPSHOT.jar mms-auth-service-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","mms-auth-service-0.0.1-SNAPSHOT.jar"]