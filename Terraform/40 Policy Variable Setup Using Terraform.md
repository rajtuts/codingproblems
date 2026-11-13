# 40 Policy Variable Setup Using Terraform

The Nautilus DevOps team is automating IAM policy creation using Terraform to enhance security and access management. As part of this task, they need to create an IAM policy with specific requirements.

For this task, create an AWS IAM policy using Terraform with the following requirements:

The IAM policy name iampolicy_ravi should be stored in a variable named KKE_iampolicy.
Note:

The configuration values should be stored in a variables.tf file.

The Terraform script should be structured with a main.tf file referencing variables.tf.

The Terraform working directory is /home/bob/terraform.

Right-click under the EXPLORER section in VS Code and select Open in Integrated Terminal to launch the terminal.

---

Follow these steps to create an AWS IAM Policy using Terraform.

### **Step 1: Navigate to Terraform directory**

```
cd /home/bob/terraform
```

### **Step 2: Update&#x20;**`variables.tf`

Add the following variable:

```
variable "KKE_iampolicy" {
  description = "IAM policy name"
  type        = string
  default     = "iampolicy_ravi"
}
```

### **Step 3: Update&#x20;**`main.tf`

Add the following resource:

```
resource "aws_iam_policy" "iam_policy" {
  name        = var.KKE_iampolicy
  description = "IAM policy created using Terraform"

  policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Effect   = "Allow"
        Action   = "ec2:Describe*"
        Resource = "*"
      }
    ]
  })
}
```

**Explanation:**

- `aws_iam_policy`: Creates an AWS IAM policy.
- `name`: References `KKE_iampolicy` from `variables.tf`.
- `policy`: Defines the permissions granted by the policy.
- `ec2:Describe*`: Allows read-only EC2 describe operations.

### **Step 4: Initialize Terraform**

```
terraform init
```

### **Step 5: Validate configuration**

```
terraform validate
```

### **Step 6: Preview and create IAM policy**

```
terraform plan
terraform apply
```

Enter `yes` when prompted.

### **Step 7: Verify IAM policy**

```
aws iam list-policies \
  --scope Local \
  --query "Policies[?PolicyName=='iampolicy_ravi'].[PolicyName,Arn]"
```

### **Expected configuration**

| Requirement        | Value                       |
| ------------------ | --------------------------- |
| Terraform resource | `aws_iam_policy.iam_policy` |
| Variable           | `KKE_iampolicy`             |
| IAM policy name    | `iampolicy_ravi`            |
| Variables file     | `variables.tf`              |
| Main file          | `main.tf`                   |
| Working directory  | `/home/bob/terraform`       |

**Important:** The example policy grants EC2 describe permissions. Keep both `.tf` files so the policy can be managed or recreated later.
