# Бронирование комнат в сети коворкингов

Бронирование комнат в сети коворкингов. Есть разные коворкинги, в каждом из которых одна или несколько комнат:
коворкинги работают круглосуточно;
комнаты бронируются целиком;
в комнатах может быть разное количество мест: от 1 до 20.

Нужно разработать бэкенд с использованием Spring Framework, который будет реализовывать такой функционал:

CRUD для коворкингов и комнат;
метод поиска свободных комнат по одному или нескольким параметрам:
содержит не меньше N мест;
свободна на протяжении периода [X, Y);
метод бронирования конкретной комнаты на период времени [X, Y) с шагом расписания 30 минут.
При запуске проекта в базе должны оказаться тестовые данные (достаточно нескольких коворкингов, комнат и броней).
Желательно, чтобы проект можно было запустить с помощью docker-compose. Можно использовать встроенную базу данных или поднять внешнюю базу в отдельном контейнере.

## Описание перменных:
- skipTests: Параметр указывает на пропуск запуска тестов при сборке проекта. Это может быть полезно в среде разработки или для быстрого развертывания.

- spring.datasource.driverClassName: Имя JDBC-драйвера для подключения к базе данных. Например, org.postgresql.Driver для PostgreSQL.
- spring.datasource.url: URL-адрес подключения к базе данных. Например, jdbc:postgresql://localhost:5432/mydatabase для подключения к базе данных PostgreSQL с именем "mydatabase".
- spring.datasource.username: Имя пользователя базы данных для аутентификации.
- spring.datasource.password: Пароль пользователя базы данных для аутентификации.
- spring.jpa.properties.hibernate.dialect: Диалект Hibernate для работы с базой данных. Например, org.hibernate.dialect.PostgreSQLDialect для PostgreSQL.
- spring.jpa.properties.hibernate.show_sql: Указывает, нужно ли выводить SQL-запросы в журнал. Значение true позволяет просматривать SQL-запросы, генерируемые Hibernate.
- flyway.user: Имя пользователя для подключения к базе данных Flyway.
- flyway.password: Пароль пользователя для подключения к базе данных Flyway.
- flyway.schemas: Схема базы данных, используемая Flyway.
- flyway.url: URL-адрес подключения к базе данных Flyway.

## Инструкиция по запуску
### 1. Запуск приложения с использованием готового JAR-файла из папки target/ с помощью Docker Compose, включающего два контейнера: контейнер для Spring Boot и контейнер для PostgreSQL.
#### 1.1 Сбилдите образ сервиса:
```
docker build -t coworking-app .
```
#### 1.2 Выполните команду из корневой директории проекта. В этом случае все зависимости и сборка jar будет происходить непосредственно в контейнере
```
docker-compose up
```

### 2. Запуск с использованием maven без jar файла. Весь процесс сборки проекта, включая загрузку зависимостей и упаковку JAR-файла, будет выполнен внутри контейнера.

#### 2.1 Закомментируйте строчки в DockerFile:
```
FROM openjdk:18-oracle
EXPOSE 8080
RUN mkdir /app
COPY target/coworking-0.0.1-SNAPSHOT.jar /app/coworking-0.0.1-SNAPSHOT.jar
WORKDIR /app
CMD ["java", "-jar", "coworking-0.0.1-SNAPSHOT.jar"]
```
#### 2.2 Раскомментируйте строчки в Dockerfile:
```
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
```
#### 2.3 Переопределите переменные в DockerFile
```
     -DskipTests \
    -Dspring.datasource.driverClassName= \
    -Dspring.datasource.url= \
    -Dspring.datasource.username= \
    -Dspring.datasource.password= \
    -Dspring.jpa.properties.hibernate.dialect= \
    -Dspring.jpa.properties.hibernate.show_sql= \
    -Dflyway.user= \
    -Dflyway.password= \
    -Dflyway.schemas= \
    -Dflyway.url= \
```
#### 2.4 Сбилдите образ сервиса:
```
docker build -t coworking-app .
```
#### 2.5 Выполните команду из корневой директории проекта. В этом случае все зависимости и сборка jar будет происходить непосредственно в контейнере
```
docker-compose up
```
## Api документация swagger доступна по адресам
```
/swagger-documentation.html
/swagger-ui/index.html
```
## P.S. Если совсем не получается запустить, то вспользуйтесь командой, подставив свои значения
```
clean spring-boot:run -Dspring.datasource.url=
-Dspring.datasource.username=
-Dspring.datasource.password=
-Dspring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
-Dspring.jpa.properties.hibernate.show_sql=true
-Dflyway.user=
-Dflyway.password=
-Dflyway.schemas=public
-Dflyway.url=
-Dflyway.locations=classpath:db/migration
```