#!/usr/bin/env bash
ISTIO_VERSION=1.15.0
curl -L https://istio.io/downloadIstio | ISTIO_VERSION=${ISTIO_VERSION} TARGET_ARCH=x86_64 sh -
sudo install istio-${ISTIO_VERSION}/bin/istioctl /usr/local/bin/istioctl
rm -r istio-${ISTIO_VERSION}
istioctl version --remote=false