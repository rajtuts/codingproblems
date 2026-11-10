# 27 Attach Policy Using Terraform
The Nautilus DevOps team has been creating a couple of services on AWS cloud. They have been breaking down the migration into smaller tasks, allowing for better control, risk mitigation, and optimization of resources throughout the migration process. Recently they came up with requirements mentioned below.

An IAM user named iamuser_mark and a policy named iampolicy_mark already exists. Use Terraform to attach the IAM policy iampolicy_mark to the IAM user iamuser_mark. The Terraform working directory is /home/bob/terraform. Update the main.tf file (do not create a separate .tf file) to attach the specified IAM policy to the IAM user.

Note: Right-click under the EXPLORER section in VS Code and select Open in Integrated Terminal to launch the terminal.

---
You need to attach the existing IAM policy `iampolicy_mark` to the existing IAM user `iamuser_mark` using Terraform.

Follow these steps in VS Code.

### Step 1: Open the Terraform working directory

Open the integrated terminal and execute:

```
cd /home/bob/terraform
ls
```

### Step 2: Update `main.tf`

Open `main.tf` and add the following Terraform resource:

```
resource "aws_iam_user_policy_attachment" "mark_policy_attachment" {
  user       = "iamuser_mark"
  policy_arn = "arn:aws:iam::ACCOUNT_ID:policy/iampolicy_mark"
}
```

Important: Replace `ACCOUNT_ID` with your AWS account ID.

To retrieve your AWS account ID, execute:

```
aws sts get-caller-identity
```

Alternatively, use this dynamic approach to avoid hardcoding the account ID:

```
data "aws_iam_policy" "mark_policy" {
  name = "iampolicy_mark"
}

resource "aws_iam_user_policy_attachment" "mark_policy_attachment" {
  user       = "iamuser_mark"
  policy_arn = data.aws_iam_policy.mark_policy.arn
}
```

I recommend the second approach.

### Step 3: Initialize Terraform

```
terraform init
```

### Step 4: Validate configuration

```
terraform validate
```

Expected output:

```
Success! The configuration is valid.
```

### Step 5: Review execution plan

```
terraform plan
```

Verify that Terraform plans to create one IAM policy attachment.

### Step 6: Apply the configuration

```
terraform apply
```

Type:

```
yes
```

### Step 7: Verify the attachment

Execute:

```
aws iam list-attached-user-policies --user-name iamuser_mark
```

You should see `iampolicy_mark` in the attached policies.

### Why do we use `aws_iam_user_policy_attachment`?

| Terraform resource               | Purpose                                            |
| -------------------------------- | -------------------------------------------------- |
| `aws_iam_user`                   | Creates an IAM user                                |
| `aws_iam_policy`                 | Creates a customer-managed IAM policy              |
| `aws_iam_user_policy_attachment` | Attaches an existing managed policy to an IAM user |

Important: We are not creating a new IAM user or policy. Both already exist. We are only establishing the policy attachment.



Expected result

&#x20;The existing IAM policy `iampolicy_mark` is successfully attached to `iamuser_mark`.
