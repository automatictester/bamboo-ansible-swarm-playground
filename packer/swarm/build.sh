#!/usr/bin/env bash

SWARM_VPC_ID=$(aws ec2 describe-vpcs \
    --filters 'Name=tag:Name,Values=Bamboo and Swarm VPC' \
    --query 'Vpcs[0].VpcId' \
    --output text
)

SWARM_SUBNET_ID=$(aws ec2 describe-subnets \
    --filters 'Name=tag:Name,Values=Swarm Subnet' \
    --query 'Subnets[0].SubnetId' \
    --output text
)

packer build \
    -var "swarm_vpc_id=${SWARM_VPC_ID}" \
    -var "swarm_subnet_id=${SWARM_SUBNET_ID}" \
    swarm.json
