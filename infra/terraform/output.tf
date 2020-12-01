output "web-server-bucket-addr" {
  value = aws_s3_bucket.client_main.website_endpoint
}

output "backend-ip-addr" {
  value = aws_eip.main.public_ip
}

output "database-internal-addr" {
  value = aws_db_instance.main.address
}

