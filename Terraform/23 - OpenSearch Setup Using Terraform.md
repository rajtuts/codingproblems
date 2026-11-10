# 23 OpenSearch Setup Using Terraform

The Nautilus DevOps team needs to set up an Amazon OpenSearch Service domain to store and search their application logs. The domain should have the following specification:

1) The domain name should be datacenter-es.

2) Use Terraform to create the OpenSearch domain. The Terraform working directory is /home/bob/terraform. Create the main.tf file (do not create a different .tf file) to accomplish this task.


Notes:

The Terraform working directory is /home/bob/terraform.

Right-click under the EXPLORER section in VS Code and select Open in Integrated Terminal to launch the terminal.

Before submitting the task, ensure that terraform plan returns No changes. Your infrastructure matches the configuration.

The OpenSearch domain creation process may take several minutes. Please wait until the domain is fully created before submitting.

---

For this task, we will use Terraform's `aws_opensearch_domain` resource to create an Amazon OpenSearch Service domain named `nautilus-es`.

### 1. Navigate to the Terraform directory

Open VS Code Integrated Terminal and execute:

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

resource "aws_opensearch_domain" "nautilus_es" {
  domain_name = "nautilus-es"
}

```

Explanation:

| Configuration           | Purpose                               |
| ----------------------- | ------------------------------------- |
| `aws` provider          | Connects Terraform to AWS             |
| `us-east-1`             | AWS deployment region                 |
| `aws_opensearch_domain` | Creates an OpenSearch domain          |
| `domain_name`           | Sets the domain name to `nautilus-es` |

The OpenSearch domain resource requires only `domain_name`; other settings are optional and AWS supplies defaults.&#x20;

[image](https://www.google.com/s2/favicons?domain=https://registry.terraform.io\&sz=32)

Terraform Registry+1



### 3. Execute Terraform commands

Run the following commands one by one.

Step 1: Initialize Terraform

```
terraform init
```

Downloads the AWS provider.

Step 2: Validate configuration

```
terraform validate
```

Checks Terraform configuration syntax.

Step 3: Review execution plan

```
terraform plan
```

Initially, you should see one resource to be created.

Step 4: Create OpenSearch domain

```
terraform apply -auto-approve
```

Wait for creation to finish

OpenSearch domain provisioning can take several minutes. Terraform will wait for AWS to complete the creation operation.

### 4. Verify OpenSearch domain

Execute:

```
aws opensearch describe-domain \
  --domain-name nautilus-es
```

Check the domain status:

```
aws opensearch describe-domain \
  --domain-name nautilus-es \
  --query 'DomainStatus.Processing'
```

Expected result:

```
false
```

Also check:

```
aws opensearch describe-domain \
  --domain-name nautilus-es \
  --query 'DomainStatus.DomainStatus'
```

### 5. Final Terraform verification

After successful creation, execute:

```
terraform plan
```

Expected final result

```
No changes.
Your infrastructure matches the configuration.
```

### Important

Do not submit the task until:

1. `nautilus-es` domain is fully created.
2. AWS reports `Processing = false`.
3. `terraform plan` reports No changes.

Only `main.tf` is required; do not create another `.tf` file.
