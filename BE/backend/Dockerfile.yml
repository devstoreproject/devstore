FROM openjdk:11
ARG JAR_FILE=build/libs/*.jar
VOLUME ["/var/log"]
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "-Duser.timezone=Asia/Seoul", "/app.jar"]