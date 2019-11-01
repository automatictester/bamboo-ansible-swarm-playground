resource "aws_iam_role" "bamboo_role" {
  name                 = "Bamboo"
  assume_role_policy   = "${file("iam-policy/assume-role-policy.json")}"
  tags {
    Terraform = "BAS"
  }
}

resource "aws_iam_instance_profile" "bamboo_instance_profile" {
  name                 = "Bamboo"
  role                 = "${aws_iam_role.bamboo_role.name}"
}

resource "aws_iam_policy" "bamboo" {
  name                 = "Bamboo"
  path                 = "/"
  policy               = <<EOF
{
    "Version": "2012-10-17",
    "Statement": [
        {
            "Sid": "PushSimpleAppImage",
            "Effect": "Allow",
            "Action": [
                "ecr:CompleteLayerUpload",
                "ecr:UploadLayerPart",
                "ecr:InitiateLayerUpload",
                "ecr:PutImage",
                "ecr:BatchCheckLayerAvailability"
            ],
            "Resource": "arn:aws:ecr:*:*:repository/simple-app"
        },
        {
            "Sid": "GetAuthorizationToken",
            "Effect": "Allow",
            "Action": "ecr:GetAuthorizationToken",
            "Resource": "*"
        }
    ]
}
EOF
}

resource "aws_iam_role_policy_attachment" "bamboo" {
  role                 = "${aws_iam_role.bamboo_role.name}"
  policy_arn           = "${aws_iam_policy.bamboo.arn}"
}
