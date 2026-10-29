# 3. Create VPC Using Terraform
Create the `main.tf` file in `/home/bob/terraform` with the following Terraform configuration.

### 1. Create `main.tf`

Open the VS Code integrated terminal and run:

```
cd /home/bob/terraform
vi main.tf
```

Press `i` and paste this code:

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

  tags = {
    Name = "devops-vpc"
  }
}

```

Save and exit by pressing `Esc`, typing `:wq`, and pressing `Enter`.

### 2. Initialize Terraform

```
terraform init
```

### 3. Validate the configuration

```
terraform validate
```

Expected output:

```
Success! The configuration is valid.
```

### 4. Create the VPC

Preview the changes:

```
terraform plan
```

Apply the configuration:

```
terraform apply
```

Type `yes` when prompted.

### 5. Verify the result

```
terraform state list
```

Expected resource:

```
aws_vpc.devops_vpc
```

Your VPC will have these properties:

| Property           | Value                         |
| ------------------ | ----------------------------- |
| VPC name           | `devops-vpc`                  |
| AWS region         | `us-east-1`                   |
| IPv4 CIDR          | `10.0.0.0/16`                 |
| Terraform resource | `aws_vpc.devops_vpc`          |
| File               | `/home/bob/terraform/main.tf` |

Important: If the lab already provides an AWS provider configuration or specifies a different provider version, retain the lab's existing configuration where appropriate. The VPC resource itself is the key requirement.
