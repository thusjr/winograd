# First stage
FROM maven:3-jdk-8-slim

ENV BACKEND=/opt/backend
WORKDIR $BACKEND

COPY . $BACKEND
RUN mvn -q -s settings.xml package -DskipTests

EXPOSE 80

CMD java -jar target/backend.jar