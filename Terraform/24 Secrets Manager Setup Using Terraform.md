# 24 Secrets Manager Setup Using Terraform

The Nautilus DevOps team needs to store sensitive data securely using AWS Secrets Manager. They need to create a secret with the following specifications:

1) The secret name should be datacenter-secret.

2) The secret value should contain a key-value pair with username: admin and password: Namin123.

3) Use Terraform to create the secret in AWS Secrets Manager.

The Terraform working directory is /home/bob/terraform. Create the main.tf file (do not create a different .tf file) to accomplish this task.

Note: Right-click under the EXPLORER section in VS Code and select Open in Integrated Terminal to launch the terminal.

---

We will use Terraform's `aws_secretsmanager_secret` and `aws_secretsmanager_secret_version` resources to create the secret and store its username/password JSON value.

### 1. Navigate to the Terraform directory

Open VS Code Integrated Terminal:

```
cd /home/bob/terraform
```

### 2. Create `main.tf`

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

resource "aws_secretsmanager_secret" "datacenter_secret" {
  name = "datacenter-secret"
}

resource "aws_secretsmanager_secret_version" "datacenter_secret_value" {
  secret_id = aws_secretsmanager_secret.datacenter_secret.id

  secret_string = jsonencode({
    username = "admin"
    password = "Namin123"
  })
}

```

### 3. Understand the resources

| Resource                            | Purpose                                                   |
| ----------------------------------- | --------------------------------------------------------- |
| `aws_secretsmanager_secret`         | Creates the secret container in AWS Secrets Manager.      |
| `aws_secretsmanager_secret_version` | Stores the actual secret value.                           |
| `secret_id`                         | Connects the secret value to the secret container.        |
| `jsonencode()`                      | Converts the username/password key-value pairs into JSON. |

The stored secret value will be:

```
{
  "username": "admin",
  "password": "Namin123"
}
```

### 4. Execute Terraform commands

Run these commands one by one.

Step 1: Initialize Terraform

```
terraform init
```

Step 2: Validate configuration

```
terraform validate
```

Step 3: Review execution plan

```
terraform plan
```

You should see two resources planned for creation.

Step 4: Apply configuration

```
terraform apply -auto-approve
```

Terraform will create the secret and store its value.

### 5. Verify the secret in AWS

Check whether the secret exists:

```
aws secretsmanager describe-secret \
  --secret-id datacenter-secret
```

Verify the secret value:

```
aws secretsmanager get-secret-value \
  --secret-id datacenter-secret \
  --query SecretString \
  --output text
```

Expected output:

```
{"username":"admin","password":"Namin123"}
```

### 6. Final verification

Run:

```
terraform plan
```

Expected:

```
No changes.
Your infrastructure matches the configuration.
```

Task completion criteria

- Secret name: `datacenter-secret`
- Username: `admin`
- Password: `Namin123`
- Resources created: 2
- File used: `main.tf`

Important security note: Terraform state can contain the secret value in plaintext. Avoid committing `terraform.tfstate` to Git, and protect the state file with secure remote storage and encryption.
