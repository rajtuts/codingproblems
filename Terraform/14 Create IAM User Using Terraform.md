# 14 Create IAM User Using Terraform

You need to create an AWS IAM group named `iamgroup_mariyam` using Terraform.

Follow these steps in your VS Code terminal.

### Step 1: Navigate to the Terraform directory

```
cd /home/bob/terraform
```

### Step 2: Create `main.tf`

```
vi main.tf
```

Add the following Terraform configuration:

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

resource "aws_iam_group" "mariyam_group" {
  name = "iamgroup_mariyam"
}

```

Save and exit:

- Press `Esc`
- Type `:wq`
- Press `Enter`

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

### Step 5: Apply Terraform

```
terraform plan
terraform apply
```

When prompted, enter:

```
yes
```

### Step 6: Verify IAM group creation

Run:

```
aws iam get-group \
  --group-name iamgroup_mariyam
```

You should see the IAM group details, including its ARN and group ID.

### Understanding the Terraform resource

| Terraform component | Purpose                            |
| ------------------- | ---------------------------------- |
| `aws_iam_group`     | Creates an AWS IAM group           |
| `mariyam_group`     | Terraform's internal resource name |
| `iamgroup_mariyam`  | Actual AWS IAM group name          |
| `us-east-1`         | AWS provider region                |

Important interview point: IAM is a global AWS service. IAM groups are not specific to an AWS region, even though the AWS provider requires a configured region.

That's it! Only `main.tf` is required. No additional `.tf` files are needed.
