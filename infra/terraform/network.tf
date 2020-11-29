# VPC Declaration
resource "aws_vpc" "vpc" {
  cidr_block           = var.cidr_vpc
  enable_dns_support   = true
  enable_dns_hostnames = true
}

resource "aws_subnet" "public_1" {
  vpc_id                  = aws_vpc.vpc.id
  cidr_block              = var.cidr_subnets[0]
  availability_zone = "${var.region}${var.primary_availability_zone_ext}"

  map_public_ip_on_launch = true
}

resource "aws_subnet" "public_2" {
  vpc_id = aws_vpc.vpc.id
  cidr_block = var.cidr_subnets[3]
  availability_zone = "${var.region}${var.secondary_availability_zone_ext}"

  map_public_ip_on_launch = true
}

##

resource "aws_subnet" "private_1" {
  vpc_id = aws_vpc.vpc.id
  cidr_block = var.cidr_subnets[1]
  availability_zone = "${var.region}${var.primary_availability_zone_ext}"
}

resource "aws_subnet" "private_2" {
  vpc_id = aws_vpc.vpc.id
  cidr_block = var.cidr_subnets[2]
  availability_zone = "${var.region}${var.secondary_availability_zone_ext}"
}

resource "aws_db_subnet_group" "main" {
  subnet_ids = [aws_subnet.private_1.id, aws_subnet.private_2.id]
}

##
resource "aws_internet_gateway" "igw" {
  depends_on = [aws_subnet.public_1, aws_subnet.public_2]
  vpc_id = aws_vpc.vpc.id
}

resource "aws_route_table" "public_subnet" {
  vpc_id = aws_vpc.vpc.id

  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = aws_internet_gateway.igw.id
  }
}

resource "aws_route_table_association" "igw_rt" {
  subnet_id = aws_subnet.public_1.id
  route_table_id = aws_route_table.public_subnet.id
}

resource "aws_route_table_association" "igw_rt_2" {
  subnet_id = aws_subnet.public_2.id
  route_table_id = aws_route_table.public_subnet.id
}

resource "aws_eip" "main" {
  depends_on = [aws_internet_gateway.igw, aws_route_table_association.igw_rt]
  vpc = true
}

##
resource "aws_nat_gateway" "main" {
  depends_on = [aws_eip.main]

  allocation_id = aws_eip.main.id
  subnet_id = aws_subnet.public_1.id
}

##
resource "aws_route_table" "nat_gw" {
  vpc_id = aws_vpc.vpc.id

  route {
    cidr_block = "0.0.0.0/0"
    nat_gateway_id = aws_nat_gateway.main.id
  }
}

##
resource "aws_route_table_association" "nat_gw_rt" {
  subnet_id = aws_subnet.private_1.id
  route_table_id = aws_route_table.nat_gw.id
}

//resource "aws_network_acl" "backend" {
//  vpc_id = aws_vpc.vpc.id
//}

resource "aws_security_group" "alb" {
  description = "HTTP"
  vpc_id = aws_vpc.vpc.id

  ingress {
    protocol = "tcp"
    from_port = 8080
    to_port = 8080
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    protocol = "-1"
    from_port = 0
    to_port = 0

    cidr_blocks = ["0.0.0.0/0"]
  }
}

##
resource "aws_security_group" "backend" {
  description = "HTTP, HTTPS, SSH"
  vpc_id = aws_vpc.vpc.id

  ingress {
    protocol = "tcp"
    from_port = 8080
    to_port = 8080
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    protocol = "tcp"
    from_port = 22
    to_port = 22
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    protocol = "ICMP"
    from_port = 0
    to_port = 0
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    protocol = "-1"
    from_port = 0
    to_port = 0

    cidr_blocks = ["0.0.0.0/0"]
  }
}

##
resource "aws_security_group" "mysql" {
  vpc_id = aws_vpc.vpc.id

  ingress {
    description = "MySQL Access"
    protocol    = "tcp"
    from_port   = 3306
    to_port     = 3306
    security_groups = [aws_security_group.backend.id]
  }

  egress {
    description = "outbound from MySQL"
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

##

resource "aws_lb" "main_lb" {
  name_prefix = "sc-lb-"
  internal = false
  load_balancer_type = "application"

  security_groups = [aws_security_group.alb.id]
  subnets = [aws_subnet.public_1.id, aws_subnet.public_2.id]
  // can set access_logs
}

resource "aws_lb_listener" "backend_endpoint" {
  load_balancer_arn = aws_lb.main_lb.arn
  port = 8080
  protocol = "HTTP"

  default_action {
    type = "forward"
    target_group_arn = aws_alb_target_group.backend_alb_tg.arn
  }
}