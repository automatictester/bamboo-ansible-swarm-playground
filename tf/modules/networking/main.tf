resource "aws_vpc" "bamboo_swarm_vpc" {
  cidr_block = "10.1.0.0/16"
  enable_dns_hostnames = true

  tags {
    Name = "Bamboo and Swarm VPC"
    Terraform = "BAS"
  }
}

resource "aws_internet_gateway" "bamboo_swarm_vpc_gw" {
  vpc_id = "${aws_vpc.bamboo_swarm_vpc.id}"

  tags {
    Name = "Bamboo and Swarm IGW"
    Terraform = "BAS"
  }
}

resource "aws_route_table" "bamboo_swarm_vpc_rt" {
  vpc_id = "${aws_vpc.bamboo_swarm_vpc.id}"

  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = "${aws_internet_gateway.bamboo_swarm_vpc_gw.id}"
  }

  tags {
    Name = "Bamboo and Swarm RT"
    Terraform = "BAS"
  }
}

resource "aws_subnet" "bamboo_subnet" {
  vpc_id = "${aws_vpc.bamboo_swarm_vpc.id}"
  cidr_block = "10.1.0.0/28"
  availability_zone = "eu-west-2a"
  map_public_ip_on_launch = true

  tags {
    Name = "Bamboo Subnet"
    Terraform = "BAS"
  }
}

resource "aws_subnet" "swarm_subnet" {
  vpc_id = "${aws_vpc.bamboo_swarm_vpc.id}"
  cidr_block = "10.1.0.16/28"
  availability_zone = "eu-west-2a"
  map_public_ip_on_launch = true

  tags {
    Name = "Swarm Subnet"
    Terraform = "BAS"
  }
}

resource "aws_route_table_association" "rt_association_bamboo_subnet" {
  subnet_id = "${aws_subnet.bamboo_subnet.id}"
  route_table_id = "${aws_route_table.bamboo_swarm_vpc_rt.id}"
}

resource "aws_route_table_association" "rt_association_swarm_subnet" {
  subnet_id = "${aws_subnet.swarm_subnet.id}"
  route_table_id = "${aws_route_table.bamboo_swarm_vpc_rt.id}"
}

resource "aws_security_group" "swarm_node" {
  name = "Swarm node"
  description = "Swarm node"
  vpc_id = "${aws_vpc.bamboo_swarm_vpc.id}"

  tags {
    Name = "Swarm node"
    Terraform = "BAS"
  }
}

resource "aws_security_group_rule" "2377_tcp" {
  security_group_id = "${aws_security_group.swarm_node.id}"
  type = "ingress"
  from_port = 2377
  to_port = 2377
  protocol = "tcp"
  self = true
}

resource "aws_security_group_rule" "7946_tcp" {
  security_group_id = "${aws_security_group.swarm_node.id}"
  type = "ingress"
  from_port = 7946
  to_port = 7946
  protocol = "tcp"
  self = true
}

resource "aws_security_group_rule" "7946_udp" {
  security_group_id = "${aws_security_group.swarm_node.id}"
  type = "ingress"
  from_port = 7946
  to_port = 7946
  protocol = "udp"
  self = true
}

resource "aws_security_group_rule" "4789_udp" {
  security_group_id = "${aws_security_group.swarm_node.id}"
  type = "ingress"
  from_port = 4789
  to_port = 4789
  protocol = "udp"
  self = true
}

resource "aws_security_group" "egress" {
  name = "Full egress"
  description = "Full egress"
  vpc_id = "${aws_vpc.bamboo_swarm_vpc.id}"

  tags {
    Name = "Full egress"
    Terraform = "BAS"
  }
}

resource "aws_security_group_rule" "allow_egress" {
  security_group_id = "${aws_security_group.egress.id}"
  type = "egress"
  from_port = 0
  to_port = 0
  protocol = "-1"
  cidr_blocks = [
    "0.0.0.0/0"
  ]
}

resource "aws_security_group" "bamboo_http" {
  name = "Bamboo HTTP access"
  description = "Allow HTTP access to Bamboo"
  vpc_id = "${aws_vpc.bamboo_swarm_vpc.id}"

  tags {
    Name = "Bamboo HTTP access"
    Terraform = "BAS"
  }
}

resource "aws_security_group_rule" "allow_ingress_8085" {
  security_group_id = "${aws_security_group.bamboo_http.id}"
  type = "ingress"
  from_port = 8085
  to_port = 8085
  protocol = "tcp"
  cidr_blocks = [
    "0.0.0.0/0"
  ]
}

resource "aws_security_group" "swarm_http" {
  name = "Swarm service HTTP access"
  description = "Swarm service HTTP access"
  vpc_id = "${aws_vpc.bamboo_swarm_vpc.id}"

  tags {
    Name = "Swarm service HTTP access"
    Terraform = "BAS"
  }
}

resource "aws_security_group_rule" "allow_ingress_8080" {
  security_group_id = "${aws_security_group.swarm_http.id}"
  type = "ingress"
  from_port = 8080
  to_port = 8080
  protocol = "tcp"
  cidr_blocks = [
    "0.0.0.0/0"
  ]
}

resource "aws_security_group" "ssh" {
  name = "SSH access"
  description = "Allow SSH access"
  vpc_id = "${aws_vpc.bamboo_swarm_vpc.id}"

  tags {
    Name = "SSH access"
    Terraform = "BAS"
  }
}

resource "aws_security_group_rule" "allow_ingress_ssh" {
  security_group_id = "${aws_security_group.ssh.id}"
  type = "ingress"
  from_port = 22
  to_port = 22
  protocol = "tcp"
  cidr_blocks = [
    "0.0.0.0/0"
  ]
}

resource "aws_security_group" "efs" {
  name = "EFS access"
  description = "Allow EFS access"
  vpc_id = "${aws_vpc.bamboo_swarm_vpc.id}"

  tags {
    Name = "EFS access"
    Terraform = "BAS"
  }
}

resource "aws_security_group_rule" "allow_ingress_efs" {
  security_group_id = "${aws_security_group.efs.id}"
  type = "ingress"
  from_port = 2049
  to_port = 2049
  protocol = "tcp"
  source_security_group_id = "${aws_security_group.bamboo_http.id}"
}

resource "aws_efs_file_system" "bamboo_home" {
  performance_mode = "generalPurpose"
  throughput_mode = "bursting"

  lifecycle_policy {
    transition_to_ia = "AFTER_14_DAYS"
  }

  tags = {
    Name = "Bamboo Home"
    Terraform = "BAS"
  }
}

resource "aws_efs_mount_target" "alpha" {
  file_system_id = "${aws_efs_file_system.bamboo_home.id}"
  subnet_id = "${aws_subnet.bamboo_subnet.id}"

  security_groups = [
    "${aws_security_group.efs.id}"
  ]
}

resource "aws_ecr_repository" "simple_app" {
  name                 = "simple-app"
  image_tag_mutability = "MUTABLE"
  tags = {
    Terraform = "BAS"
  }
}

resource "aws_ecr_lifecycle_policy" "expire_untagged" {
  repository = "${aws_ecr_repository.simple_app.name}"

  policy = <<EOF
{
    "rules": [
        {
            "rulePriority": 1,
            "description": "Expire untagged images older than 1 day",
            "selection": {
                "tagStatus": "untagged",
                "countType": "sinceImagePushed",
                "countUnit": "days",
                "countNumber": 1
            },
            "action": {
                "type": "expire"
            }
        },
        {
            "rulePriority": 2,
            "description": "Expire images older than 7 days",
            "selection": {
                "tagStatus": "any",
                "countType": "sinceImagePushed",
                "countUnit": "days",
                "countNumber": 7
            },
            "action": {
                "type": "expire"
            }
        }
    ]
}
EOF
}
