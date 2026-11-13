# 33 Delete VPC Using Terraform

The Nautilus DevOps team is strategically planning the migration of a portion of their infrastructure to the AWS cloud. Acknowledging the magnitude of this endeavor, they have chosen to tackle the migration incrementally rather than as a single, massive transition. They created some services in different regions and later found that some of those can be deleted now.

Delete a VPC named devops-vpc present in us-east-1 region using Terraform. Make sure to keep the provisioning code, as we might need to provision this instance again later.

The Terraform working directory is /home/bob/terraform.

Note: Right-click under the EXPLORER section in VS Code and select Open in Integrated Terminal to launch the terminal.

---

You can delete the `devops-vpc` in AWS `us-east-1` using Terraform while keeping all Terraform provisioning files intact.

Follow these steps in VS Code.

### **Step 1: Open Terraform working directory**

Open the integrated terminal and execute:

```
cd /home/bob/terraform
```

### **Step 2: Initialize Terraform**

```
terraform init
```

### **Step 3: Verify the AWS region**

Check your Terraform provider configuration:

```
grep -n -A5 'provider "aws"' *.tf
```

Make sure the region is configured as:

```
provider "aws" {
  region = "us-east-1"
}
```

### **Step 4: Identify the VPC resource**

Check Terraform state:

```
terraform state list
```

Find the resource corresponding to `devops-vpc`.

For example:

```
aws_vpc.devops_vpc
```

Verify its details:

```
terraform state show aws_vpc.devops_vpc
```

Confirm that its `Name` tag is `devops-vpc`.

### **Step 5: Preview the deletion**

Run:

```
terraform plan -destroy -target=aws_vpc.devops_vpc
```

Review the plan and ensure it targets the intended VPC and any required dependencies.

### **Step 6: Delete the VPC**

Execute:

```
terraform destroy -target=aws_vpc.devops_vpc
```

When prompted, enter:

```
yes
```

Terraform will destroy the targeted resource and any required dependencies.

### **Step 7: Verify deletion**

Check Terraform state again:

```
terraform state list
```

Verify that the VPC resource is no longer present.

You can also verify in AWS CLI:

```
aws ec2 describe-vpcs \
  --region us-east-1 \
  --filters "Name=tag:Name,Values=devops-vpc"
```

### **Important: Preserve Terraform provisioning code**

Keep the code

**Do not delete or modify your&#x20;**`.tf`**&#x20;files.**

- `main.tf` – Keep the VPC resource definition.
- `provider.tf` – Keep AWS provider configuration.
- `variables.tf` – Keep variable definitions.
- `terraform.tfstate` – Terraform manages this state file.

**Expected result:**

- VPC `devops-vpc` deleted from `us-east-1`.
- Terraform provisioning code preserved.
- VPC can be recreated later using `terraform apply`.
