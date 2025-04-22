#!/bin/bash
set -e

ENDPOINT="--endpoint-url=http://localhost:4566"

aws $ENDPOINT cloudformation delete-stack \
    --stack-name patient-management

aws $ENDPOINT cloudformation deploy \
    --stack-name patient-management \
    --template-file "./cdk.out/localstack.template.json"

aws $ENDPOINT elbv2 describe-load-balancers \
    --query "LoadBalancers[0].DNSName" --output text
