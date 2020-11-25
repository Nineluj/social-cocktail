resource "aws_db_instance" "main" {
  allocated_storage = 0.05
  instance_class = "false"
  username = "sc"
  password = var.db_password
  # Will need a social-cocktail/ db: TODO with ansible
}
