# -----------------------------------------------------------------------------
# Build frontend with webpack
# -----------------------------------------------------------------------------
FROM node:8.1.3-alpine AS frontend

RUN set -xe \
    && npm install --global webpack@2.5.1

WORKDIR /work

COPY package.json package-lock.json webpack.config.js ./

RUN set -xe \
    && npm install \
    && npm cache clean --force

COPY ./vue ./vue

RUN webpack

# -----------------------------------------------------------------------------
# Build VirtualStack app
# -----------------------------------------------------------------------------
FROM java:8-jdk-alpine AS backend

ENV SBT_VERSION=0.13.15 SBT_HOME=/usr/local/sbt
ENV PATH ${PATH}:${SBT_HOME}/bin

RUN set -xe \
    && apk update \
    && apk add --no-cache \
        ca-certificates \
        curl \
        unzip \
        bash

RUN set -xe \
    && curl -sL "http://dl.bintray.com/sbt/native-packages/sbt/$SBT_VERSION/sbt-$SBT_VERSION.tgz" \
        | gunzip \
        | tar -x -C /usr/local \
    && sbt update

WORKDIR /work

COPY build.sbt ./
COPY project/* ./project/
COPY ./modules ./modules

RUN sbt update

COPY ./app ./app
COPY ./conf ./conf
COPY ./public ./public
COPY ./test ./test

COPY --from=frontend /work/public/javascripts/* public/javascripts/

RUN set -xe \
    && sbt dist \
    && unzip target/universal/virtualstack-0.1.0.zip

# -----------------------------------------------------------------------------
# Copy application
# -----------------------------------------------------------------------------
FROM java:8-jre-alpine

MAINTAINER Shoma Nishitateno <shoma416@gmail.com>

ENV VIRTUALSTACK_DB_HOST=virtualstack-db VIRTUALSTACK_DB_NAME=virtualstack VIRTUALSTACK_DB_USER=virtualstack VIRTUALSTACK_DB_PASS=virtualstack

RUN set -xe \
    && apk update \
    && apk add --no-cache \
        postgresql-client=9.5.7-r0

COPY --from=backend /work/virtualstack-0.1.0 /app

COPY docker-entrypoint.sh /

ENTRYPOINT ["/docker-entrypoint.sh"]