#!/usr/bin/env bash

EFS_FILESYSTEM_ID=$(aws efs describe-file-systems \
    --query 'FileSystems[?Name == `Bamboo Home`].FileSystemId' \
    --output text
)

BAMBOO_VPC_ID=$(aws ec2 describe-vpcs \
    --filters 'Name=tag:Name,Values=Bamboo and Swarm VPC' \
    --query 'Vpcs[0].VpcId' \
    --output text
)

BAMBOO_SUBNET_ID=$(aws ec2 describe-subnets \
    --filters 'Name=tag:Name,Values=Bamboo Subnet' \
    --query 'Subnets[0].SubnetId' \
    --output text
)

BAMBOO_HTTP_ACCESS_SG_ID=$(aws ec2 describe-security-groups \
    --filters 'Name=tag:Name,Values=Bamboo HTTP access' 'Name=tag:Terraform,Values=BAS' \
    --query 'SecurityGroups[0].GroupId' \
    --output text
)

BAMBOO_SSH_ACCESS_SG_ID=$(aws ec2 describe-security-groups \
    --filters 'Name=tag:Name,Values=SSH access' 'Name=tag:Terraform,Values=BAS' \
    --query 'SecurityGroups[0].GroupId' \
    --output text
)

packer build \
    -var "efs_filesystem_id=${EFS_FILESYSTEM_ID}" \
    -var "bamboo_vpc_id=${BAMBOO_VPC_ID}" \
    -var "bamboo_subnet_id=${BAMBOO_SUBNET_ID}" \
    -var "bamboo_http_access_sg_id=${BAMBOO_HTTP_ACCESS_SG_ID}" \
    -var "bamboo_ssh_access_sg_id=${BAMBOO_SSH_ACCESS_SG_ID}" \
    bamboo.json
