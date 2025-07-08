terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "5.95.0"
    }
  }
  backend "s3" {
    bucket = "authentication-backend-tfstate"
    key    = "terraform.tfstate"
    region = "us-east-1"
  }
}

provider "aws" {
  profile = "sacha"
  region  = "us-east-1"
}