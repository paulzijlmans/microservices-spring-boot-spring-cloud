#!/usr/bin/env bash
./gradlew build && docker-compose build && ./test-em-all.bash start stop