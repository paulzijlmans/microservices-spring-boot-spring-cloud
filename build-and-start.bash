#!/usr/bin/env bash
./gradlew clean build && docker-compose build && docker-compose up -d