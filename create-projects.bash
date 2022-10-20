#!/usr/bin/env bash

mkdir microservices
cd microservices

spring init \
--boot-version=2.7.4 \
--build=gradle \
--java-version=17 \
--packaging=jar \
--name=product-service \
--package-name=nl.paulzijlmans.microservices.core.product \
--groupId=nl.paulzijlmans.microservices.core.product \
--dependencies=actuator,webflux \
--version=1.0.0-SNAPSHOT \
product-service

spring init \
--boot-version=2.7.4 \
--build=gradle \
--java-version=17 \
--packaging=jar \
--name=review-service \
--package-name=nl.paulzijlmans.microservices.core.review \
--groupId=nl.paulzijlmans.microservices.core.review \
--dependencies=actuator,webflux \
--version=1.0.0-SNAPSHOT \
review-service

spring init \
--boot-version=2.7.4 \
--build=gradle \
--java-version=17 \
--packaging=jar \
--name=recommendation-service \
--package-name=nl.paulzijlmans.microservices.core.recommendation \
--groupId=nl.paulzijlmans.microservices.core.recommendation \
--dependencies=actuator,webflux \
--version=1.0.0-SNAPSHOT \
recommendation-service

spring init \
--boot-version=2.7.4 \
--build=gradle \
--java-version=17 \
--packaging=jar \
--name=product-composite-service \
--package-name=nl.paulzijlmans.microservices.composite.product \
--groupId=nl.paulzijlmans.microservices.composite.product \
--dependencies=actuator,webflux \
--version=1.0.0-SNAPSHOT \
product-composite-service

cd ..