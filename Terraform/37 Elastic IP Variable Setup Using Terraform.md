# 37 Elastic IP Variable Setup Using Terraform

The Nautilus DevOps team is strategizing the migration of a portion of their infrastructure to the AWS cloud. As part of this phased migration approach, they need to allocate an Elastic IP address to support external access for specific workloads.

For this task, create an AWS Elastic IP using Terraform with the following requirement:

The Elastic IP name devops-eip should be stored in a variable named KKE_eip. The Terraform working directory is /home/bob/terraform.
Note:

The configuration values should be stored in a variables.tf file.

The Terraform script should be structured with a main.tf file referencing variables.tf.

Right-click under the EXPLORER section in VS Code and select Open in Integrated Terminal to launch the terminal.

---
Follow these steps to create an AWS Elastic IP using Terraform.

### **Step 1: Navigate to Terraform directory**

```
cd /home/bob/terraform
```

### **Step 2: Update&#x20;**`variables.tf`

Add the following variable:

```
variable "KKE_eip" {
  description = "Elastic IP name"
  type        = string
  default     = "devops-eip"
}
```

### **Step 3: Update&#x20;**`main.tf`

Add the Elastic IP resource:

```
resource "aws_eip" "devops_eip" {
  domain = "vpc"

  tags = {
    Name = var.KKE_eip
  }
}
```

**Explanation:**

- `aws_eip`: Terraform resource for creating an Elastic IP.
- `domain = "vpc"`: Allocates the Elastic IP for use with a VPC.
- `tags`: Assigns the name `devops-eip` using the variable `KKE_eip`.

### **Step 4: Initialize Terraform**

```
terraform init
```

### **Step 5: Validate configuration**

```
terraform validate
```

### **Step 6: Preview and create Elastic IP**

```
terraform plan
terraform apply
```

Enter `yes` when prompted.

### **Step 7: Verify Elastic IP**

```
aws ec2 describe-addresses \
  --region us-east-1 \
  --filters "Name=tag:Name,Values=devops-eip"
```

### **Expected configuration**

| Requirement        | Value                |
| ------------------ | -------------------- |
| Terraform resource | `aws_eip.devops_eip` |
| Variable           | `KKE_eip`            |
| Elastic IP name    | `devops-eip`         |
| Domain             | `vpc`                |
| Configuration file | `main.tf`            |
| Variables file     | `variables.tf`       |

**Important:** Keep both `.tf` files. Terraform automatically loads `variables.tf` and `main.tf` from `/home/bob/terraform`.
