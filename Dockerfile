FROM openjdk
VOLUME /tmp
ADD educational-portal-api/target/educational-portal-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]