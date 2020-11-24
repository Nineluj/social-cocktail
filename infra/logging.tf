resource "aws_cloudwatch_log_group" "social-cocktail" {
  name_prefix = "social-cocktail"
}

resource "aws_cloudtrail" "frontend" {
  name = "sc-frontend-trail"
  s3_bucket_name = aws_s3_bucket.frontend.arn
  cloud_watch_logs_group_arn = aws_cloudwatch_log_group.social-cocktail.arn
}