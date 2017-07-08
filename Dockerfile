FROM node:8.1.3-alpine AS frontend

RUN set -xe \
    && npm install --global webpack@2.5.1

COPY package.json package-lock.json webpack.config.js ./

RUN set -xe \
    && npm install \
    && npm cache clean --force

COPY ./vue ./vue

RUN webpack