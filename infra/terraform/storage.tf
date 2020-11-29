# DB
resource "aws_db_instance" "main" {
  allocated_storage = 20
  instance_class = "db.t2.micro"
  username = "sc"
  password = var.db_password
  engine = "mysql"
  engine_version = "8.0"
  # TODO: change this to keep the snapshot
  skip_final_snapshot = true

  publicly_accessible = false
  availability_zone = aws_subnet.private_1.availability_zone

  db_subnet_group_name = aws_db_subnet_group.main.name
  vpc_security_group_ids = [aws_security_group.mysql.id]

//  provisioner "local-exec" {
//    command = <<EOF
//ansible-playbook ../playbooks/database.yaml --extra-vars "DB_USER=${self.username} DB_PASSWORD=${var.db_password} DB_HOST=${self.address}"
//EOF
//  }
}

# S3
data "aws_iam_policy_document" "website_policy" {
  statement {
    sid = "PublicReadGetObject"
    effect = "Allow"

    actions = [
      "s3:GetObject",
    ]

    principals {
      identifiers = ["*"]
      type = "AWS"
    }

    resources = [
      "${aws_s3_bucket.client_main.arn}/*",
    ]
  }
}

//resource "aws_iam_policy" "website_policy" {
//  policy = data.aws_iam_policy_document.website_policy.json
//}

resource "aws_s3_bucket_policy" "website_bucket_policy" {
  bucket = aws_s3_bucket.client_main.bucket
  policy = data.aws_iam_policy_document.website_policy.json
}

resource "aws_s3_bucket" "client_main" {
  bucket_prefix = "scp-main-"
  acl = "public-read"

  website {
    index_document = "index.html"
    error_document = "index.html"
  }
}

resource "aws_s3_bucket" "client_scripts" {
  bucket_prefix = "sc-scripts-"
}

resource "aws_s3_bucket" "logs" {
  bucket_prefix = "sc-logs-"
  acl = "private"
}