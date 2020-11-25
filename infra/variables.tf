variable "cidr_vpc" {
  description = "CIDR block for the VPC"
  default     = "10.1.0.0/16"
}

variable "cidr_subnet" {
  description = "CIDR block for the subnet"
  default     = "10.1.0.0/24"
}

variable "environment_tag" {
  description = "Environment tag"
  default     = "social-cocktail"
}

variable "region" {
  description = "The region Terraform deploys your instance"
}

variable "db_password" {
  description = "The password to use for the database"
  type = string
}