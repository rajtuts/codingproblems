# 18 Create Kinesis Stream Using Terraform

The Nautilus DevOps team needs to create an AWS Kinesis data stream for real-time data processing. This stream will be used to ingest and process large volumes of streaming data, which will then be consumed by various applications for analytics and real-time decision-making.

The stream should be named nautilus-stream.

Use Terraform to create this Kinesis stream.

The Terraform working directory is /home/bob/terraform. Create the main.tf file (do not create a different .tf file) to accomplish this task.

Note:

Right-click under the EXPLORER section in VS Code and select Open in Integrated Terminal to launch the terminal.
Before submitting the task, ensure that terraform plan returns No changes. Your infrastructure matches the configuration.


--- 

You need to create an AWS Kinesis Data Stream named `nautilus-stream` using Terraform.

Follow these steps.

### Step 1: Navigate to the Terraform directory

```
cd /home/bob/terraform
```

### Step 2: Create `main.tf`

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

resource "aws_kinesis_stream" "nautilus_stream" {
  name             = "nautilus-stream"
  shard_count      = 1
  retention_period = 24
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

### Step 5: Create the Kinesis stream

```
terraform plan
terraform apply
```

Enter `yes` when prompted.

### Step 6: Verify the stream

```
aws kinesis describe-stream \
  --stream-name nautilus-stream \
  --region us-east-1
```

### Step 7: Important — Check for No Changes

Run:

```
terraform plan
```

Expected output

```
No changes. Your infrastructure matches the configuration.
```

### Understanding the configuration

| Attribute               | Purpose                       |
| ----------------------- | ----------------------------- |
| `aws_kinesis_stream`    | Creates a Kinesis Data Stream |
| `name`                  | Stream name                   |
| `shard_count = 1`       | Creates one shard             |
| `retention_period = 24` | Retains records for 24 hours  |

Important: The task specifies only the stream name. One shard and 24-hour retention are reasonable defaults for this lab. The Kinesis stream is created in `us-east-1`.

Remember: Do not delete `terraform.tfstate` or run `terraform destroy` before the final `terraform plan`. Terraform needs its state to recognize the already-created stream and report no changes.
