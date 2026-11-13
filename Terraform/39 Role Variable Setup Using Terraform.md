# 39 Role Variable Setup Using Terraform

The Nautilus DevOps team is automating IAM role creation using Terraform to streamline permissions management. As part of this task, they need to create an IAM role with specific requirements.

For this task, create an AWS IAM role using Terraform with the following requirements:

The IAM role name iamrole_james should be stored in a variable named KKE_iamrole.
Note:

1. The configuration values should be stored in a variables.tf file.

2. The Terraform script should be structured with a main.tf file referencing variables.tf.
The Terraform working directory is /home/bob/terraform.

Right-click under the EXPLORER section in VS Code and select Open in Integrated Terminal to launch the terminal.

---

Follow these steps to create an AWS IAM Role using Terraform.

### **Step 1: Navigate to Terraform directory**

```
cd /home/bob/terraform
```

### **Step 2: Update&#x20;**`variables.tf`

Add the following variable:

```
variable "KKE_iamrole" {
  description = "IAM role name"
  type        = string
  default     = "iamrole_james"
}
```

### **Step 3: Update&#x20;**`main.tf`

Add the following Terraform resource:

```
resource "aws_iam_role" "iam_role" {
  name = var.KKE_iamrole

  assume_role_policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Effect = "Allow"
        Principal = {
          Service = "ec2.amazonaws.com"
        }
        Action = "sts:AssumeRole"
      }
    ]
  })
}
```

**Explanation:**

- `aws_iam_role`: Creates an AWS IAM role.
- `name`: Gets the role name from `variables.tf`.
- `assume_role_policy`: Defines who can assume this role.
- `ec2.amazonaws.com`: Allows EC2 instances to assume the role.

### **Step 4: Initialize Terraform**

```
terraform init
```

### **Step 5: Validate configuration**

```
terraform validate
```

### **Step 6: Preview and create IAM role**

```
terraform plan
terraform apply
```

Enter `yes` when prompted.

### **Step 7: Verify IAM role**

```
aws iam get-role --role-name iamrole_james
```

### **Expected configuration**

| Requirement        | Value                   |
| ------------------ | ----------------------- |
| Terraform resource | `aws_iam_role.iam_role` |
| Variable           | `KKE_iamrole`           |
| IAM role name      | `iamrole_james`         |
| Variables file     | `variables.tf`          |
| Main file          | `main.tf`               |

**Important:** The trust policy above allows EC2 to assume the role. If the task only checks role creation and naming, this is a valid example. Keep both Terraform files for future updates.
