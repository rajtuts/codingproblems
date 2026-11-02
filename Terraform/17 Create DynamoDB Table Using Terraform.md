# 17 Create DynamoDB Table Using Terraform

The Nautilus DevOps team needs to set up a DynamoDB table for storing user data. They need to create a DynamoDB table with the following specifications:

1) The table name should be xfusion-users.

2) The primary key should be xfusion_id (String).

3) The table should use PAY_PER_REQUEST billing mode.

Use Terraform to create this DynamoDB table. The Terraform working directory is /home/bob/terraform. Create the main.tf file (do not create a different .tf file) to create the DynamoDB table.

Note: Right-click under the EXPLORER section in VS Code and select Open in Integrated Terminal to launch the terminal.

---
You need to create an AWS DynamoDB table named `xfusion-users` using Terraform with a String partition key and on-demand billing.

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

resource "aws_dynamodb_table" "xfusion_users" {
  name         = "xfusion-users"
  billing_mode = "PAY_PER_REQUEST"
  hash_key     = "xfusion_id"

  attribute {
    name = "xfusion_id"
    type = "S"
  }
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

Expected:

```
Success! The configuration is valid.
```

### Step 5: Review execution plan

```
terraform plan
```

Expected:

```
Plan: 1 to add, 0 to change, 0 to destroy.
```

### Step 6: Create DynamoDB table

```
terraform apply
```

Enter `yes` when prompted.

Expected result

```
Apply complete! Resources: 1 added, 0 changed, 0 destroyed.
```

### Step 7: Verify the DynamoDB table

```
aws dynamodb describe-table \
  --table-name xfusion-users \
  --region us-east-1
```

### Understanding the Terraform configuration

| Attribute            | Purpose                   |
| -------------------- | ------------------------- |
| `aws_dynamodb_table` | Creates a DynamoDB table  |
| `name`               | Table name                |
| `hash_key`           | Partition key             |
| `attribute`          | Defines the key attribute |
| `type = "S"`         | String data type          |
| `PAY_PER_REQUEST`    | On-demand billing         |

Important interview points:

1. `hash_key` defines the partition key.
2. DynamoDB uses `S`, `N`, and `B` for String, Number, and Binary attribute types.
3. `PAY_PER_REQUEST` automatically scales based on application traffic, without requiring provisioned read/write capacity settings.
4. The `attribute` block defines key attributes, not every possible item attribute.

That's it! Only `main.tf` is required.
