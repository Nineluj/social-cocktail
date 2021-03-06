variable "cidr_vpc" {
  description = "CIDR block for the VPC"
  default     = "10.0.0.0/16"
}

variable "cidr_subnets" {
  default = ["10.0.0.0/24", "10.0.1.0/24", "10.0.2.0/24", "10.0.3.0/24"]
  type = list(string)
}

variable "environment_tag" {
  description = "Environment tag"
  default     = "social-cocktail"
}

variable "region" {
  description = "The region Terraform deploys your instance"
}


variable "primary_availability_zone_ext" {
  description = "Primary availability zone to use"
  default = "a"
}

variable "secondary_availability_zone_ext" {
  description = "Secondary availability zone to use"
  default = "b"
}

variable "db_password" {
  description = "The password to use for the database"
  type = string
}

variable "packer_ami_id" {
  description = "AMI ID generated by packer containing the server executable"
  type = string
}

variable "public_key_path" {
  description = "The path to a public key (.pub). Provided so that you can SSH into the EC2 instances."
  type = string
}

//variable "use-snapshot-base" {
//  type = bool
//}