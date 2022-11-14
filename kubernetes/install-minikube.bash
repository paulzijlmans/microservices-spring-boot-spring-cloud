#!/usr/bin/env bash
OS=darwin
MINIKUBE_VERSION=1.28.0
curl -LO https://storage.googleapis.com/minikube/releases/v${MINIKUBE_VERSION}/minikube-${OS}-arm64
sudo install minikube-${OS}-arm64 /usr/local/bin/minikube
rm minikube-${OS}-arm64
minikube version --short