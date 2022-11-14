#!/usr/bin/env bash
K8S_VERSION=1.25.3
curl -LO "https://dl.k8s.io/release/v${K8S_VERSION}/bin/darwin/amd64/kubectl"
sudo install kubectl /usr/local/bin/kubectl
rm kubectl
kubectl version --client --output=json | jq -r .clientVersion.gitVersion