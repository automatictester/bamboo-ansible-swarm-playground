resource "aws_iam_role" "swarm_role" {
  name                 = "Swarm"
  assume_role_policy   = "${file("iam-policy/assume-role-policy.json")}"
  tags {
    Terraform = "BAS"
  }
}

resource "aws_iam_instance_profile" "swarm_instance_profile" {
  name                 = "Swarm"
  role                 = "${aws_iam_role.swarm_role.name}"
}

resource "aws_iam_policy" "swarm" {
  name                 = "Swarm"
  path                 = "/"
  policy               = <<EOF
{
    "Version": "2012-10-17",
    "Statement": [
        {
            "Sid": "PullSimpleAppImage",
            "Effect": "Allow",
            "Action": [
                "ecr:BatchGetImage",
                "ecr:GetDownloadUrlForLayer"
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

resource "aws_iam_role_policy_attachment" "swarm" {
  role                 = "${aws_iam_role.swarm_role.name}"
  policy_arn           = "${aws_iam_policy.swarm.arn}"
}
