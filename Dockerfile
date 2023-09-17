FROM openjdk:18-oracle
EXPOSE 8080
RUN mkdir /app
COPY target/coworking-0.0.1-SNAPSHOT.jar /app/coworking-0.0.1-SNAPSHOT.jar
WORKDIR /app
CMD ["java", "-jar", "coworking-0.0.1-SNAPSHOT.jar"]

#Без Jar
#FROM openjdk:18-jdk-slim
#RUN apt-get update && apt-get install -y maven
#EXPOSE 8080
#WORKDIR /app
#COPY pom.xml .
#COPY src ./src
#RUN mvn clean package \
#    -DskipTests \
#    -Dspring.datasource.driverClassName= \
#    -Dspring.datasource.url= \
#    -Dspring.datasource.username= \
#    -Dspring.datasource.password= \
#    -Dspring.jpa.properties.hibernate.dialect= \
#    -Dspring.jpa.properties.hibernate.show_sql= \
#    -Dflyway.user= \
#    -Dflyway.password= \
#    -Dflyway.schemas= \
#    -Dflyway.url= \
#CMD ["java", "-jar", "target/coworking-0.0.1-SNAPSHOT.jar"]