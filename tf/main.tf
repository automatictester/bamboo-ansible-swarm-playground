terraform {
  backend "s3" {
    bucket  = "automatictester.co.uk-bamboo-ansible-swarm-state"
    key     = "main.tfstate"
    region  = "eu-west-2"
  }
}

provider "aws" {
  region    = "eu-west-2"
}

module "bamboo" {
  source    = "./modules/bamboo"
}

module "networking" {
  source    = "./modules/networking"
}

module "swarm" {
  source    = "./modules/swarm"
}
