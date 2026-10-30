# 7 Create EC2 Instance Using Terraform

For this task, you need to create an EC2 instance with an RSA key pair and attach the default security group using Terraform.

We will define everything in a single file: `/home/bob/terraform/main.tf`.

## 1. Terraform configuration

Open the VS Code terminal:

```
cd /home/bob/terraform
vi main.tf
```

Add the following configuration:

```

terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 5.0"
    }

    tls = {
      source  = "hashicorp/tls"
      version = "~> 4.0"
    }
  }
}

provider "aws" {
  region = "us-east-1"
}

# Get the default VPC
data "aws_vpc" "default" {
  default = true
}

# Get the default security group
data "aws_security_group" "default" {
  name   = "default"
  vpc_id = data.aws_vpc.default.id
}

# Generate a new RSA private/public key pair
resource "tls_private_key" "datacenter" {
  algorithm = "RSA"
  rsa_bits  = 4096
}

# Register the public key with AWS
resource "aws_key_pair" "datacenter_kp" {
  key_name   = "datacenter-kp"
  public_key = tls_private_key.datacenter.public_key_openssh
}

# Create EC2 instance
resource "aws_instance" "datacenter_ec2" {
  ami           = "ami-0c101f26f147fa7fd"
  instance_type = "t2.micro"

  key_name = aws_key_pair.datacenter_kp.key_name

  vpc_security_group_ids = [
    data.aws_security_group.default.id
  ]

  tags = {
    Name = "datacenter-ec2"
  }
}

```

## 2. Initialize Terraform

```
terraform init
```

This downloads the AWS and TLS providers.

## 3. Validate configuration

```
terraform fmt
terraform validate
```

Expected:

```
Success! The configuration is valid.
```

## 4. Create AWS resources

```
terraform plan
terraform apply
```

Type `yes` when prompted.

Terraform creates these resources:

1. RSA private/public key pair.
2. AWS EC2 key pair named `datacenter-kp`.
3. EC2 instance named `datacenter-ec2`.
4. Attaches the default security group.

## 5. Verify the resources

```
terraform state list
```

Expected:

```
data.aws_security_group.default
data.aws_vpc.default
aws_key_pair.datacenter_kp
aws_instance.datacenter_ec2
tls_private_key.datacenter
```

Inspect the EC2 instance:

```
terraform state show aws_instance.datacenter_ec2
```

## 6. Important Terraform concepts

| Terraform resource/data          | Purpose                      |
| -------------------------------- | ---------------------------- |
| `tls_private_key`                | Generates RSA keys           |
| `aws_key_pair`                   | Registers public key in AWS  |
| `aws_vpc` data source            | Finds default VPC            |
| `aws_security_group` data source | Finds default security group |
| `aws_instance`                   | Creates EC2 instance         |

Important: The generated private key is stored in Terraform state. Protect your state file and don't commit it to Git.

Configuration ready

You can now run `terraform apply` to provision the EC2 instance.
