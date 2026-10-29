# 2. Create Security Group Using Terraform
Create `/home/bob/terraform/main.tf` with the following Terraform configuration. It uses the default VPC in `us-east-1` and creates the required security group with HTTP and SSH inbound rules.

```

provider "aws" {
  region = "us-east-1"
}

data "aws_vpc" "default" {
  default = true
}

resource "aws_security_group" "nautilus_sg" {
  name        = "nautilus-sg"
  description = "Security group for Nautilus App Servers"
  vpc_id      = data.aws_vpc.default.id

  ingress {
    description = "HTTP"
    from_port   = 80
    to_port     = 80
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    description = "SSH"
    from_port   = 22
    to_port     = 22
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

```

## Run Terraform

Open the integrated terminal in VS Code and execute:

```
cd /home/bob/terraform
terraform init
terraform plan
terraform apply
```

When prompted, type `yes`.

## Verify the security group

```
aws ec2 describe-security-groups \
  --group-names nautilus-sg \
  --region us-east-1
```

Important: This configuration creates only the requested inbound rules. The AWS provider's default outbound rule behavior applies unless you explicitly configure egress.
