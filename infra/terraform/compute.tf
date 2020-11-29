//resource "aws_instance" "backend-server" {
//  depends_on = [aws_key_pair.project-key, aws_db_instance.main]
//
//  ami                         = var.packer_ami_id
//  instance_type               = "t2.micro"
//  subnet_id                   = aws_subnet.public_1.id
//
//  vpc_security_group_ids      = [aws_security_group.backend.id]
//  key_name = aws_key_pair.project-key.key_name
//
//  connection {
//    type = "ssh"
//    user = "ec2-user"
//    private_key = file("resources/key.pem")
//    host = self.public_ip
//  }
//
//  provisioner "remote-exec" {
//    inline = [
//      "while [ ! -f /var/lib/cloud/instance/boot-finished ]; do echo 'Waiting for cloud-init...'; sleep 1; done",
//      "export DB_PASSWORD=${var.db_password} DB_URL=mysql://${aws_db_instance.main.address}/social_cocktail_db && screen -d -m java -jar /app/app.jar",
//      "sleep 1" // need to sleep here since screen doesn't seem to get time to run without it
//    ]
//  }
//}

resource "aws_key_pair" "project-key" {
  public_key = file("resources/key.pub")
}

## ASG setup
resource "aws_placement_group" "main" {
  name = "sc-placement-group"
  strategy = "cluster"
}

resource "aws_launch_configuration" "main" {
  depends_on = [aws_db_instance.main]

  name_prefix = "sc-backend-"
  image_id = var.packer_ami_id
  instance_type = "t2.micro"
  associate_public_ip_address = true

  key_name = aws_key_pair.project-key.key_name
  security_groups = [aws_security_group.backend.id]

  // putting password here isn't great, should use AWS SecretsManager
  user_data_base64 = base64encode(<<EOF
#!/bin/bash
export DB_PASSWORD=${var.db_password} DB_URL=mysql://${aws_db_instance.main.address}/social_cocktail_db && screen -d -m java -jar /app/app.jar
EOF
)

  lifecycle {
    create_before_destroy = true
  }
}

resource "aws_alb_target_group" "backend_alb_tg" {
  vpc_id = aws_vpc.vpc.id
  port = 8080
  protocol = "HTTP"

  health_check {
    path = "/actuator/health"
    port = 8080
    matcher = "200"
  }
}

//resource "aws_autoscaling_attachment" "asg_alb" {
//  autoscaling_group_name = aws_autoscaling_group.backend.id
//  alb_target_group_arn = aws_alb_target_group.backend_alb_tg.arn
//}

resource "aws_autoscaling_group" "backend" {
  name = aws_launch_configuration.main.name
  min_size = 1
  max_size = 3
  health_check_grace_period = 60
  health_check_type = "EC2"

  launch_configuration = aws_launch_configuration.main.name
  vpc_zone_identifier = [aws_subnet.public_1.id]

  target_group_arns = [aws_alb_target_group.backend_alb_tg.arn]

  lifecycle {
    create_before_destroy = true
  }
}

//data "aws_launch_configuration" "launch_config" {
//  name = aws_launch_configuration.main.name
//}


