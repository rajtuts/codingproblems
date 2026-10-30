# 5 Create VPC with IPv6 Using Terraform

For this task, you need to create an AWS VPC named `devops-vpc` in `us-east-1` and enable an Amazon-provided IPv6 CIDR block.

### 1. Navigate to Terraform directory

```
cd /home/bob/terraform
```

### 2. Create `main.tf`

```
vi main.tf
```

Add this configuration:

```

terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 5.0"
    }
  }
}

provider "aws" {
  region = "us-east-1"
}

resource "aws_vpc" "devops_vpc" {
  cidr_block = "10.0.0.0/16"
  assign_generated_ipv6_cidr_block = true

  tags = {
    Name = "devops-vpc"
  }
}

```

### 3. Initialize Terraform

```
terraform init
```

### 4. Validate configuration

```
terraform validate
```

Expected:

```
Success! The configuration is valid.
```

### 5. Apply Terraform

```
terraform plan
terraform apply
```

Type `yes` when prompted.

### 6. Verify VPC creation

```
terraform state list
```

Expected:

```
aws_vpc.devops_vpc
```

Inspect the resource:

```
terraform state show aws_vpc.devops_vpc
```

Look for:

```
assign_generated_ipv6_cidr_block = true
ipv6_cidr_block                 = "2600:..."
```

### Important interview concept

What does `assign_generated_ipv6_cidr_block = true` do?

It instructs AWS to automatically associate an Amazon-provided IPv6 CIDR block with the VPC.

- You don't manually specify an IPv6 CIDR.
- AWS allocates the IPv6 CIDR block.
- The VPC can use IPv4 and IPv6 networking.

Remember:

| Requirement          | Terraform argument                        |
| -------------------- | ----------------------------------------- |
| Custom IPv4 CIDR     | `cidr_block`                              |
| Amazon-provided IPv6 | `assign_generated_ipv6_cidr_block = true` |
| VPC name             | `tags.Name`                               |
