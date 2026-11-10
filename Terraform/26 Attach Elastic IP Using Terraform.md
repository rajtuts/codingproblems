# 26 Attach Elastic IP Using Terraform

The Nautilus DevOps team has been creating a couple of services on AWS cloud. They have been breaking down the migration into smaller tasks, allowing for better control, risk mitigation, and optimization of resources throughout the migration process. Recently they came up with requirements mentioned below.

There is an instance named devops-ec2 and an elastic-ip named devops-ec2-eip in us-east-1 region. Attach the devops-ec2-eip elastic-ip to the devops-ec2 instance using Terraform only. The Terraform working directory is /home/bob/terraform. Update the main.tf file (do not create a separate .tf file) to attach the specified Elastic IP to the instance.

Note: Right-click under the EXPLORER section in VS Code and select Open in Integrated Terminal to launch the terminal.

---

We need to attach the existing Elastic IP `devops-ec2-eip` to the existing EC2 instance `devops-ec2` in `us-east-1` using Terraform only.

We will use Terraform's `aws_eip_association` resource. This associates an existing Elastic IP with an existing EC2 instance without creating a new Elastic IP or EC2 instance.

## 1. Navigate to the working directory

Open VS Code Integrated Terminal:

```
cd /home/bob/terraform
```

## 2. Update `main.tf`

First, inspect your existing configuration:

```
cat main.tf
```

Keep any existing Terraform resources. Add the following configuration to `main.tf`:

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

# Find the existing EC2 instance
data "aws_instance" "devops_ec2" {
  filter {
    name   = "tag:Name"
    values = ["devops-ec2"]
  }

  filter {
    name   = "instance-state-name"
    values = ["running"]
  }
}

# Find the existing Elastic IP
data "aws_eip" "devops_ec2_eip" {
  filter {
    name   = "tag:Name"
    values = ["devops-ec2-eip"]
  }
}

# Associate Elastic IP with EC2 instance
resource "aws_eip_association" "devops_ec2_eip_association" {
  instance_id   = data.aws_instance.devops_ec2.id
  allocation_id = data.aws_eip.devops_ec2_eip.id
}

```

How it works:

| Terraform resource    | Purpose                                               |
| --------------------- | ----------------------------------------------------- |
| `data.aws_instance`   | Finds the existing EC2 instance using its Name tag.   |
| `data.aws_eip`        | Finds the existing Elastic IP using its Name tag.     |
| `aws_eip_association` | Attaches the existing Elastic IP to the EC2 instance. |
| `allocation_id`       | Identifies the Elastic IP allocation.                 |
| `instance_id`         | Identifies the target EC2 instance.                   |

## 3. Initialize Terraform

```
terraform init
```

## 4. Validate configuration

```
terraform validate
```

Expected:

```
Success! The configuration is valid.
```

## 5. Review execution plan

```
terraform plan
```

Expected: Terraform should show one resource to be created:

```
Plan: 1 to add, 0 to change, 0 to destroy.
```

## 6. Apply Terraform configuration

```
terraform apply -auto-approve
```

Terraform will associate the existing Elastic IP with the existing EC2 instance.

## 7. Verify the association

Run:

```
terraform plan
```

Expected:

```
No changes.
Your infrastructure matches the configuration.
```

Expected final configuration

EC2 instance\
devops-ec2

Elastic IP\
devops-ec2-eip

Region\
us-east-1

Association

Completed

Important: This configuration assumes both existing AWS resources have the specified names in their `Name` tags. It does not create a new EC2 instance or Elastic IP. If your existing `main.tf` contains other resources, preserve them and add these blocks rather than replacing the entire file.
