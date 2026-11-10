# 21 - CloudWatch Setup Using Terraform
The Nautilus DevOps team needs to create an SSM parameter in AWS with the following requirements:

1) The name of the parameter should be devops-ssm-parameter.

2) Set the parameter type to String.

3) Set the parameter value to devops-value.

4) The parameter should be created in the us-east-1 region.

5) Ensure the parameter is successfully created using terraform and can be retrieved when the task is completed.

The Terraform working directory is /home/bob/terraform. Create the main.tf file (do not create a different .tf file) to accomplish this task.

Note: Right-click under the EXPLORER section in VS Code and select Open in Integrated Terminal to launch the terminal.

---
To complete this task, you need to create the `main.tf` file inside the `/home/bob/terraform` directory using the `aws_ssm_parameter` resource with the specified requirements.

Here is the exact code you need to put into **`main.tf`**:

```hcl
resource "aws_ssm_parameter" "example" {
  name  = "devops-ssm-parameter"
  type  = "String"
  value = "devops-value"
}

```

*(Note: Ensure your provider configuration in `provider.tf` targets the `us-east-1` region as required).*

### Steps to Apply:

1. Open the integrated terminal in VS Code (Right-click under the **EXPLORER** section or use the shortcut `Ctrl` + `Shift` + ```).
2. Navigate to the working directory:
```bash
cd /home/bob/terraform

```


3. Create and edit the `main.tf` file, pasting the configuration above.
4. Initialize and apply the Terraform configuration:
```bash
terraform init
terraform apply -auto-approve

```


5. Once applied successfully, return to the KodeKloud task page and click **Check**.
