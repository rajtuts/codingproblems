# 6 Create Elastic IP Using Terraform

For this task, you need to allocate an AWS Elastic IP (EIP) named `nautilus-eip` using Terraform in the `us-east-1` region.

### 1. Navigate to the Terraform directory

```
cd /home/bob/terraform
```

### 2. Create `main.tf`

```
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
  }
}

provider "aws" {
  region = "us-east-1"
}

resource "aws_eip" "nautilus_eip" {
  domain = "vpc"

  tags = {
    Name = "nautilus-eip"
  }
}

```

### 3. Initialize Terraform

```
terraform init
```

### 4. Validate the configuration

```
terraform validate
```

Expected:

```
Success! The configuration is valid.
```

### 5. Create the Elastic IP

```
terraform plan
terraform apply
```

Type `yes` when prompted.

### 6. Verify the Elastic IP

```
terraform state list
```

Expected output:

```
aws_eip.nautilus_eip
```

Inspect the resource:

```
terraform state show aws_eip.nautilus_eip
```

Check:

| Property  | Expected        |
| --------- | --------------- |
| Resource  | `aws_eip`       |
| Name tag  | `nautilus-eip`  |
| Domain    | `vpc`           |
| Region    | `us-east-1`     |
| Public IP | AWS-assigned IP |

### Important interview concept

What is an Elastic IP?

An Elastic IP is a static public IPv4 address allocated to your AWS account. You can associate it with supported AWS resources such as EC2 instances or network interfaces.

Why `domain = "vpc"`?

It allocates the Elastic IP for use with a VPC rather than the legacy EC2-Classic platform.

Task configuration ready
