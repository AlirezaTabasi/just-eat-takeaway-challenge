FROM openjdk:11-jdk
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} player1.jar
ENTRYPOINT ["java","-jar","/player1.jar"]