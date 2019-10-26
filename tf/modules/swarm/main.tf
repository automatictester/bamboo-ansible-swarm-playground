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
