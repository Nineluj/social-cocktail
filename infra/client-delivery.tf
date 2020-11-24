resource "aws_s3_bucket" "frontend" {
  website {
    index_document = "index.html"
    error_document = "error.html"
  }

}

resource "aws_s3_bucket" "frontend_scripts" {

}

