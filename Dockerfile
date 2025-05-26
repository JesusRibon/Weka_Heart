FROM eclipse-temurin:17.0.15_6-jdk

EXPOSE 8080

WORKDIR /root

COPY ./pom.xml /root
COPY ./.mvn /root/.mvn
COPY ./mvnw /root
COPY ./mvnw.cmd /root

RUN ./mvnw dependency:go-offline

COPY ./src /root/src

RUN ./mvnw clean install -DskipTests

ENTRYPOINT ["java","-jar","/root/target/weka_heart-0.0.1-SNAPSHOT.jar"]
