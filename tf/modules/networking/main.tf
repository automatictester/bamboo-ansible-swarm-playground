resource "aws_vpc" "bamboo_swarm_vpc" {
  cidr_block = "10.1.0.0/16"
  enable_dns_hostnames = true

  tags {
    Name = "Bamboo and Swarm VPC"
  }
}

resource "aws_internet_gateway" "bamboo_swarm_vpc_gw" {
  vpc_id = "${aws_vpc.bamboo_swarm_vpc.id}"

  tags {
    Name = "Bamboo and Swarm IGW"
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
  }
}

resource "aws_subnet" "bamboo_subnet" {
  vpc_id = "${aws_vpc.bamboo_swarm_vpc.id}"
  cidr_block = "10.1.0.0/28"
  availability_zone = "eu-west-2a"
  map_public_ip_on_launch = true

  tags {
    Name = "Bamboo Subnet"
  }
}

resource "aws_subnet" "swarm_subnet" {
  vpc_id = "${aws_vpc.bamboo_swarm_vpc.id}"
  cidr_block = "10.1.0.16/28"
  availability_zone = "eu-west-2a"
  map_public_ip_on_launch = true

  tags {
    Name = "Swarm Subnet"
  }
}

resource "aws_route_table_association" "rt_association_bamboo_subnet" {
  subnet_id      = "${aws_subnet.bamboo_subnet.id}"
  route_table_id = "${aws_route_table.bamboo_swarm_vpc_rt.id}"
}

resource "aws_route_table_association" "rt_association_swarm_subnet" {
  subnet_id      = "${aws_subnet.swarm_subnet.id}"
  route_table_id = "${aws_route_table.bamboo_swarm_vpc_rt.id}"
}

resource "aws_security_group" "bamboo_swarm" {
  name = "Internal Bamboo and Swarm"
  description = "Allow full connectivity between Bamboo and Swarm hosts"
  vpc_id = "${aws_vpc.bamboo_swarm_vpc.id}"

  ingress {
    from_port = 0
    to_port = 0
    protocol = "-1"
    self = true
  }

  egress {
    from_port = 0
    to_port = 0
    protocol = "-1"
    cidr_blocks = [
      "0.0.0.0/0"
    ]
  }

  tags {
    Name = "Internal Bamboo and Swarm"
  }
}

resource "aws_security_group" "bamboo" {
  name = "Bamboo HTTP access"
  description = "Allows HTTP access to Bamboo"
  vpc_id = "${aws_vpc.bamboo_swarm_vpc.id}"

  tags {
    Name = "Bamboo HTTP access"
  }
}

resource "aws_security_group_rule" "allow_ingress_http" {
  security_group_id = "${aws_security_group.bamboo.id}"
  type = "ingress"
  from_port = 8085
  to_port = 8085
  protocol = "tcp"
  cidr_blocks = [
    "0.0.0.0/0"
  ]
}

resource "aws_security_group_rule" "allow_egress_allow_all" {
  security_group_id = "${aws_security_group.bamboo.id}"
  type = "egress"
  from_port = 0
  to_port = 0
  protocol = "-1"
  cidr_blocks = [
    "0.0.0.0/0"
  ]
}
