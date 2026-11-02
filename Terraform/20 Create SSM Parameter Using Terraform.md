# 20 Create SSM Parameter Using Terraform

The Nautilus DevOps team needs to create an SSM parameter in AWS with the following requirements:

1) The name of the parameter should be xfusion-ssm-parameter.

2) Set the parameter type to String.

3) Set the parameter value to xfusion-value.

4) The parameter should be created in the us-east-1 region.

5) Ensure the parameter is successfully created using terraform and can be retrieved when the task is completed.

The Terraform working directory is /home/bob/terraform. Create the main.tf file (do not create a different .tf file) to accomplish this task.

Note: Right-click under the EXPLORER section in VS Code and select Open in Integrated Terminal to launch the terminal.

---

You need to create an AWS SSM Parameter Store parameter named `xfusion-ssm-parameter` using Terraform.

Follow these steps.

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

resource "aws_ssm_parameter" "xfusion_parameter" {
  name  = "xfusion-ssm-parameter"
  type  = "String"
  value = "xfusion-value"
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

### Step 5: Create the SSM parameter

```
terraform plan
terraform apply
```

Enter `yes` when prompted.

Expected result

```
Apply complete! Resources: 1 added, 0 changed, 0 destroyed.
```

### Step 6: Verify the parameter

Run:

```
aws ssm get-parameter \
  --name xfusion-ssm-parameter \
  --region us-east-1
```

Expected response (simplified):

```
{
    "Parameter": {
        "Name": "xfusion-ssm-parameter",
        "Type": "String",
        "Value": "xfusion-value"
    }
}
```

### Understanding the Terraform configuration

| Attribute           | Purpose                                   |
| ------------------- | ----------------------------------------- |
| `aws_ssm_parameter` | Creates an SSM Parameter Store parameter  |
| `name`              | Parameter name                            |
| `type = "String"`   | Stores a plain-text string                |
| `value`             | Parameter value                           |
| `region`            | AWS region where the parameter is created |

Important interview point:

AWS Systems Manager Parameter Store stores configuration data and secrets centrally. Common parameter types are:

- `String` – Plain-text values.
- `StringList` – Comma-separated string values.
- `SecureString` – Encrypted values using AWS KMS.

That's it! Only `main.tf` is required. Your parameter should now be available in Parameter Store in `us-east-1`.
