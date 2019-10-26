resource "aws_iam_role" "packer_role" {
  name                 = "PackerForBambooAndSwarm"
  assume_role_policy   = "${file("iam-policy/assume-role-policy.json")}"
  tags {
    Terraform = "BAS"
  }
}

resource "aws_iam_instance_profile" "packer_instance_profile" {
  name                 = "PackerForBambooAndSwarm"
  role                 = "${aws_iam_role.packer_role.name}"
}
