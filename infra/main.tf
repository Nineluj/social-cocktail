provider "aws" {
  region = var.region
}

output "web-server" {
  value = aws_s3_bucket.frontend.website
}

//output "id" {
//  value = aws_instance.backend-server.public_ip
//}
