resource "aws_instance" "backend-server" {
  ami                         = "ami-092471fca5d4b2584"
  instance_type               = "t2.micro"
  subnet_id                   = aws_subnet.subnet_public.id
  vpc_security_group_ids      = [aws_security_group.sg_22_80.id]
  associate_public_ip_address = true

  tags = {
    Name = ""
  }
}