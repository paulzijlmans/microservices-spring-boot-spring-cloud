#!/usr/bin/env bash

unset KUBECONFIG

minikube start \
--profile=handson-spring-boot-cloud \
--memory=6144 \
--cpus=4 \
--disk-size=30g \
--kubernetes-version=v1.25.3 \
--driver=docker \
--ports=8080:80 --ports=8443:443 \
--ports=30080:30080 --ports=30443:30443

minikube profile handson-spring-boot-cloud

minikube addons enable ingress
minikube addons enable metrics-server
