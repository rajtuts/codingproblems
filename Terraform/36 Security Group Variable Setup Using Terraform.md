# 36 Security Group Variable Setup Using Terraform

The Nautilus DevOps team is enhancing infrastructure automation and needs to provision a Security Group using Terraform with specific configurations.

For this task, create an AWS Security Group using Terraform with the following requirements:

The Security Group name xfusion-sg should be stored in a variable named KKE_sg.
Note:

1. The configuration values should be stored in a variables.tf file.

2. The Terraform script should be structured with a main.tf file referencing variables.tf.
The Terraform working directory is /home/bob/terraform.

Right-click under the EXPLORER section in VS Code and select Open in Integrated Terminal to launch the terminal.

---

Follow these steps to create an AWS Security Group using Terraform.

### **Step 1: Navigate to Terraform directory**

```
cd /home/bob/terraform
```

### **Step 2: Update&#x20;**`variables.tf`

Add the following variable:

```
variable "KKE_sg" {
  description = "Security Group name"
  type        = string
  default     = "xfusion-sg"
}
```

### **Step 3: Update&#x20;**`main.tf`

Add the Security Group resource:

```
resource "aws_security_group" "xfusion_sg" {
  name        = var.KKE_sg
  description = "Security group created using Terraform"
}
```

This creates a Security Group with the required name. No inbound or outbound rules are specified.

### **Step 4: Initialize Terraform**

```
terraform init
```

### **Step 5: Validate configuration**

```
terraform validate
```

Expected output:

```
Success! The configuration is valid.
```

### **Step 6: Create Security Group**

```
terraform plan
terraform apply
```

Enter `yes` when prompted.

### **Step 7: Verify creation**

```
aws ec2 describe-security-groups \
  --filters "Name=group-name,Values=xfusion-sg" \
  --region us-east-1
```

### **Expected configuration**

| Requirement             | Value                |
| ----------------------- | -------------------- |
| Resource                | `aws_security_group` |
| Terraform resource name | `xfusion_sg`         |
| Variable                | `KKE_sg`             |
| Security Group name     | `xfusion-sg`         |
| Variables file          | `variables.tf`       |
| Main file               | `main.tf`            |

**Important:** Keep the variable in `variables.tf` and the Security Group resource in `main.tf`. Terraform automatically loads both files from the working directory.
