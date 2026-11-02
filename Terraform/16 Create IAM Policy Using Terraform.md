# 16 Create IAM Policy Using Terraform

For this task, you need to create an AWS IAM customer-managed policy named `iampolicy_james` using Terraform.

The policy must provide read-only access to EC2, allowing users to view:

- EC2 instances
- Amazon Machine Images (AMIs)
- EBS snapshots

We will use the AWS managed-policy-equivalent read-only EC2 permissions in a custom IAM policy.

## Step 1: Navigate to the Terraform directory

```
cd /home/bob/terraform
```

## Step 2: Create `main.tf`

```
vi main.tf
```

Add the following configuration:

```

terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "5.91.0"
    }
  }
}

provider "aws" {
  region = "us-east-1"
}

resource "aws_iam_policy" "james_policy" {
  name        = "iampolicy_james"
  description = "Read-only access to Amazon EC2 resources"

  policy = jsonencode({
    Version = "2012-10-17"

    Statement = [
      {
        Effect = "Allow"

        Action = [
          "ec2:Describe*"
        ]

        Resource = "*"
      }
    ]
  })
}

```

Save and exit:

- Press `Esc`
- Type `:wq`
- Press `Enter`

## Step 3: Initialize Terraform

```
terraform init
```

## Step 4: Validate Terraform configuration

```
terraform validate
```

Expected output:

```
Success! The configuration is valid.
```

## Step 5: Review the execution plan

```
terraform plan
```

You should see:

```
Plan: 1 to add, 0 to change, 0 to destroy.
```

## Step 6: Apply the configuration

```
terraform apply
```

Enter `yes` when prompted.

Expected result

```
Apply complete! Resources: 1 added, 0 changed, 0 destroyed.
```

## Step 7: Verify IAM policy creation

Run:

```
aws iam list-policies \
  --scope Local \
  --region us-east-1 \
  --query "Policies[?PolicyName=='iampolicy_james'].[PolicyName,Arn]" \
  --output table
```

## Understanding the IAM policy

| Configuration      | Purpose                                  |
| ------------------ | ---------------------------------------- |
| `aws_iam_policy`   | Creates a customer-managed IAM policy    |
| `name`             | Sets policy name                         |
| `ec2:Describe*`    | Allows EC2 read-only Describe operations |
| `Resource = "*"`   | Applies to all supported EC2 resources   |
| `Effect = "Allow"` | Grants the specified permissions         |
| `us-east-1`        | Configures the AWS provider region       |

Important interview concept:

- `ec2:Describe*` allows users to view EC2 information.
- It does not grant permission to create, modify, start, stop, or terminate EC2 instances.
- `Resource = "*"` is required because EC2 Describe API actions generally do not support resource-level permissions.

Note: This policy covers EC2 Describe APIs, including instances, AMIs, and snapshots. It does not automatically grant every auxiliary permission that every EC2 console feature might require.

Only `main.tf` is required for this task.
