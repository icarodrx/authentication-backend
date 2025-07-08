resource "aws_security_group" "main" {
  name        = "${var.project_name}-sg"
  description = "Allow HTTP for everyone and SSH for me"
  vpc_id      = aws_vpc.main.id

  tags = {
    Name = "${var.project_name}-sg"
  }
}

resource "aws_vpc_security_group_ingress_rule" "allow_http" {
  security_group_id = aws_security_group.main.id
  from_port         = 8080
  to_port           = 8080
  ip_protocol       = "tcp"
  cidr_ipv4         = "0.0.0.0/0"
}

resource "aws_vpc_security_group_ingress_rule" "allow_ssh" {
  security_group_id = aws_security_group.main.id
  from_port         = 22
  to_port           = 22
  ip_protocol       = "tcp"
  cidr_ipv4         = "${data.external.local_ip.result.ip}/32"
}

resource "aws_vpc_security_group_egress_rule" "allow_all_traffic_ipv4" {
  security_group_id = aws_security_group.main.id
  cidr_ipv4         = "0.0.0.0/0"
  ip_protocol       = "-1"
}

resource "aws_instance" "ec2" {
  ami                    = "ami-0e449927258d45bc4" # Amazon Linux 2023 AMI
  instance_type          = "t2.micro"
  subnet_id              = aws_subnet.pub_1a.id
  vpc_security_group_ids = [aws_security_group.main.id]
  key_name               = var.key_pair_name
  iam_instance_profile   = aws_iam_instance_profile.ec2_profile.name

  root_block_device {
    volume_size           = 8
    volume_type           = "gp3"
    encrypted             = true
    delete_on_termination = true
  }

  user_data = templatefile("${path.module}/templates/ec2_user_data.sh.tpl", {
    repository_url = var.repository_url
    project_name   = var.project_name
  })

  tags = {
    Name = "${var.project_name}-ec2"
  }
}