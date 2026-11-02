# 15 Create IAM Group Using Terraform

You need to create an AWS IAM group named `iamgroup_john` using Terraform.

### Step 1: Navigate to the working directory

```
cd /home/bob/terraform
```

### Step 2: Create or update `main.tf`

```
vi main.tf
```

Add this configuration:

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

resource "aws_iam_group" "john_group" {
  name = "iamgroup_john"
}

```

Save the file using `Esc`, `:wq`, and `Enter`.

### Step 3: Initialize Terraform

```
terraform init
```

### Step 4: Validate configuration

```
terraform validate
```

Expected:

```
Success! The configuration is valid.
```

### Step 5: Create the IAM group

```
terraform plan
terraform apply
```

Enter `yes` when prompted.

### Step 6: Verify the IAM group

```
aws iam get-group --group-name iamgroup_john
```

Expected result

You should see the IAM group details, including its ARN and GroupId.

Important: IAM is a global AWS service, so the group is not restricted to `us-east-1`.

Only `main.tf` is needed for this task.
