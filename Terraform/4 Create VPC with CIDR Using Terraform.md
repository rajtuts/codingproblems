# 4 Create VPC with CIDR Using Terraform
You need to create a single `main.tf` file in `/home/bob/terraform` to provision the AWS VPC.

### 1. Create `main.tf`

Open the terminal in VS Code and execute:

```
cd /home/bob/terraform
vi main.tf
```

Add the following Terraform configuration:

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

resource "aws_vpc" "xfusion_vpc" {
  cidr_block = "192.168.0.0/24"

  tags = {
    Name = "xfusion-vpc"
  }
}

```

### 2. Save the file

If using `vi`:

- Press `Esc`
- Type `:wq`
- Press `Enter`

### 3. Initialize Terraform

```
terraform init
```

This downloads the AWS provider plugin.

### 4. Validate configuration

```
terraform validate
```

Expected output:

```
Success! The configuration is valid.
```

### 5. Create the VPC

```
terraform plan
terraform apply
```

Type `yes` when prompted.

### 6. Verify the resource

```
terraform state list
```

Expected output:

```
aws_vpc.xfusion_vpc
```

To inspect the VPC:

```
terraform state show aws_vpc.xfusion_vpc
```

Check these values:

| Property      | Expected value |
| ------------- | -------------- |
| Resource type | aws_vpc        |
| Resource name | xfusion_vpc    |
| AWS region    | us-east-1      |
| Name tag      | xfusion-vpc    |
| IPv4 CIDR     | 192.168.0.0/24 |

Important: Terraform resource address (`aws_vpc.xfusion_vpc`) and AWS VPC Name tag (`xfusion-vpc`) are different. Both are intentional.



Task complete

&#x20;Your VPC should now be provisioned in AWS.
