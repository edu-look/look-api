FROM openjdk:17-jdk-slim-buster

RUN apt-get update && apt-get install -y tesseract-ocr wget

WORKDIR /usr/src/app
COPY . /usr/src/app

RUN rm -rf tokens data ; \
	mkdir -p data/ ; \
	wget https://github.com/tesseract-ocr/tessdata_best/raw/main/por.traineddata -P ./data

RUN ./mvnw clean install -Dmaven.test.skip=true

ENV LOOK_GCLOUD_CLIENT_ID=
ENV LOOK_GCLOUD_CLIENT_SECRET=
ENV LOOK_GCLOUD_SERVER_CLIENT_ID=
ENV LOOK_GCLOUD_SERVER_CLIENT_SECRET=
ENV LOOK_APPLICATION_NAME=look-service
ENV LOOK_APPLICATION_LOCAL_DATA=./data
ENV LOOK_GCLOUD_TOKEN_STORAGE=tokens
ENV LOOK_GCLOUD_PROJECT_ID=look-project-400817
ENV LOOK_SERVER_PORT=8085
ENV GOOGLE_APPLICATION_CREDENTIALS=

EXPOSE 8085

COPY target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
