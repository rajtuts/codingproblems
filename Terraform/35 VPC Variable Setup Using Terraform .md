# 35 VPC Variable Setup Using Terraform

The Nautilus DevOps team is automating VPC creation using Terraform to manage networking efficiently. As part of this task, they need to create a VPC with specific requirements.

For this task, create an AWS VPC using Terraform with the following requirements:

The VPC name datacenter-vpc should be stored in a variable named KKE_vpc.

The VPC should have a CIDR block of 10.0.0.0/16.

Note:

The configuration values should be stored in a variables.tf file.

The Terraform script should be structured with a main.tf file referencing variables.tf.

The Terraform working directory is /home/bob/terraform.

Right-click under the EXPLORER section in VS Code and select Open in Integrated Terminal to launch the terminal.

---

Follow these steps to create the AWS VPC using Terraform.

### **Step 1: Navigate to Terraform directory**

```
cd /home/bob/terraform
```

### **Step 2: Create&#x20;**`variables.tf`

Open `variables.tf` and add:

```
variable "KKE_vpc" {
  description = "VPC name"
  type        = string
  default     = "datacenter-vpc"
}

variable "vpc_cidr" {
  description = "VPC CIDR block"
  type        = string
  default     = "10.0.0.0/16"
}
```

### **Step 3: Update&#x20;**`main.tf`

Add the following resource:

```
resource "aws_vpc" "datacenter_vpc" {
  cidr_block = var.vpc_cidr

  tags = {
    Name = var.KKE_vpc
  }
}
```

### **Step 4: Initialize Terraform**

```
terraform init
```

### **Step 5: Validate configuration**

```
terraform validate
```

Expected:

```
Success! The configuration is valid.
```

### **Step 6: Create the VPC**

```
terraform plan
terraform apply
```

Enter `yes` when prompted.

### **Step 7: Verify VPC creation**

```
aws ec2 describe-vpcs \
  --region us-east-1 \
  --filters "Name=tag:Name,Values=datacenter-vpc" \
  --query "Vpcs[*].[VpcId,CidrBlock,Tags]"
```

### **Expected configuration**

| Requirement    | Value                    |
| -------------- | ------------------------ |
| VPC resource   | `aws_vpc.datacenter_vpc` |
| Variable name  | `KKE_vpc`                |
| VPC Name tag   | `datacenter-vpc`         |
| CIDR block     | `10.0.0.0/16`            |
| Variables file | `variables.tf`           |
| Main file      | `main.tf`                |

**Important:** Keep the variable definitions in `variables.tf` and the VPC resource in `main.tf`. Terraform automatically loads both files from the same directory.
