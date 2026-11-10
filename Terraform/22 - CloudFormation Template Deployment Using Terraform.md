# 22 - CloudFormation Template Deployment Using Terraform

The Nautilus DevOps team is working on automating infrastructure deployment using AWS CloudFormation. As part of this effort, they need to create a CloudFormation stack that provisions an S3 bucket with versioning enabled.

Create a CloudFormation stack named nautilus-stack using Terraform. This stack should contain an S3 bucket named nautilus-bucket-200675613 as a resource, and the bucket must have versioning enabled. The Terraform working directory is /home/bob/terraform. Create the main.tf file (do not create a different .tf file) to accomplish this task.

Note: Right-click under the EXPLORER section in VS Code and select Open in Integrated Terminal to launch the terminal.

---

You need to create an AWS CloudFormation stack using Terraform. Terraform will create the CloudFormation stack, and CloudFormation will provision the S3 bucket with versioning enabled.

### 1. Navigate to the Terraform working directory

Open VS Code integrated terminal and execute:

```
cd /home/bob/terraform
```

### 2. Create `main.tf`

Open `main.tf` and add the following configuration:

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

resource "aws_cloudformation_stack" "nautilus_stack" {
  name = "nautilus-stack"

  template_body = jsonencode({
    AWSTemplateFormatVersion = "2010-09-09"

    Resources = {
      NautilusBucket = {
        Type = "AWS::S3::Bucket"

        Properties = {
          BucketName = "nautilus-bucket-200675613"

          VersioningConfiguration = {
            Status = "Enabled"
          }
        }
      }
    }
  })
}

```

### 3. Execute Terraform commands

Run these commands one by one in the VS Code terminal.

Step 1: Initialize Terraform

```
terraform init
```

Why? Downloads the AWS provider required to communicate with AWS.

Step 2: Validate configuration

```
terraform validate
```

Why? Checks whether the Terraform configuration is syntactically valid.

Step 3: Review execution plan

```
terraform plan
```

Why? Shows the infrastructure Terraform is going to create.

Step 4: Apply configuration

```
terraform apply -auto-approve
```

Why? Creates the CloudFormation stack in AWS, which then creates the S3 bucket.

### 4. Expected AWS architecture

Terraform

main.tf

AWS CloudFormation\
Stack: nautilus-stack

Amazon S3

Bucket: nautilus-bucket-200675613

Versioning Enabled

### 5. Verify the stack

Check CloudFormation stack:

```
aws cloudformation describe-stacks \
  --stack-name nautilus-stack
```

Check S3 bucket versioning:

```
aws s3api get-bucket-versioning \
  --bucket nautilus-bucket-200675613
```

Expected output:

```
{
    "Status": "Enabled"
}
```

Task complete

Important interview concept: Terraform is provisioning and managing the CloudFormation stack, while CloudFormation is provisioning the actual S3 bucket. This is an example of using Terraform to orchestrate AWS-native infrastructure as code.
