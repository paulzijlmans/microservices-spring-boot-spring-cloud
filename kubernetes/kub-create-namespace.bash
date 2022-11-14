#!/usr/bin/env bash
kubectl create namespace hands-on
kubectl config set-context $(kubectl config current-context) --namespace=hands-on
