module "secrets-manager" {
  source = "lgallard/secrets-manager/aws"

  secrets = [
    {
      name = "DB_PASSWORD"
      description = "Password for the MySQL instance"
      secret_string = var.db_password
    }
  ]

  tags = {
    Project = "social-cocktail"
  }
}

resource "aws_iam_instance_profile" "db_password_retriever" {
  name = "secrets_access_instance_role"
  role = aws_iam_role.secrets_access.name
}

resource "aws_iam_role" "secrets_access" {
  name = "secrets_access_role"
  assume_role_policy = data.aws_iam_policy_document.instance_assume_role_policy.json

  lifecycle {
    create_before_destroy = true
  }
}

resource "aws_iam_role_policy_attachment" "secrets_access_policy" {
  policy_arn = aws_iam_policy.secrets_access.arn
  role = aws_iam_role.secrets_access.name
}

//resource "aws_iam_role_policy" "policy" {
//  policy = data.aws_iam_policy_document.get_secrets_value.json
//  role = aws_iam_role.secrets_access.id
//}

resource "aws_iam_policy" "secrets_access" {
  description = "Policy that grants a role access to the social cocktail secret"
  policy = data.aws_iam_policy_document.get_secrets_value.json
}

data "aws_iam_policy_document" "instance_assume_role_policy" {
  statement {
    actions = ["sts:AssumeRole"]
    effect = "Allow"

    principals {
      type = "Service"
      identifiers = ["ec2.amazonaws.com"]
    }
  }
}

data "aws_iam_policy_document" "get_secrets_value" {
  statement {
    effect = "Allow"
    actions = ["secretsmanager:GetSecretValue"]
    resources = [module.secrets-manager.secret_arns[0]]
  }
}
