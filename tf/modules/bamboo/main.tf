resource "aws_iam_role" "bamboo_role" {
  name                 = "Bamboo"
  assume_role_policy   = "${file("iam-policy/assume-role-policy.json")}"
}

resource "aws_iam_instance_profile" "bamboo_instance_profile" {
  name                 = "Bamboo"
  role                 = "${aws_iam_role.bamboo_role.name}"
}
