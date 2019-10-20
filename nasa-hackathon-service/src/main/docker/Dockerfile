FROM openjdk:8-jre-alpine

RUN apk add --update bash tcpdump && rm -rf /var/cache/apk/*

ENV APP_DIR /app
ENV APP_LIB_DIR /app/lib/

RUN echo "Creating app directory: $APP_DIR"
RUN mkdir -p $APP_DIR

RUN echo "Creating app lib directory: $APP_LIB_DIR"
RUN mkdir -p $APP_LIB_DIR

RUN echo "Copying jars into app lib directory: $APP_LIB_DIR"
COPY ./maven/* $APP_LIB_DIR

RUN echo "Copying: entrypoint.sh"
COPY ./maven/entrypoint.sh /entrypoint.sh

RUN echo "Copying: app.jar"
COPY ./maven/*.jar app.jar

ENTRYPOINT ["/entrypoint.sh"]
