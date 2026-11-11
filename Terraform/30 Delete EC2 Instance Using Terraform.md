# 30 Delete EC2 Instance Using Terraform

During the migration process, several resources were created under the AWS account. Some of these test resources are no longer needed at the moment, so we need to clean them up temporarily. One such instance is currently unused and should be deleted.

1) Delete the ec2 instance named datacenter-ec2 present in us-east-1 region using terraform. Make sure to keep the provisioning code, as we might need to provision this instance again later.

2) Before submitting your task, make sure instance is in terminated state.

The Terraform working directory is /home/bob/terraform.

Note: Right-click under the EXPLORER section in VS Code and select Open in Integrated Terminal to launch the terminal.

---

For this KodeKloud task, the requirement is to terminate the existing EC2 instance but preserve its Terraform provisioning code so it can be recreated later.

The important point is: Do not delete the EC2 resource block from `main.tf`. Instead, temporarily remove the instance from Terraform state and terminate it using the AWS CLI.

## Step 1: Open the Terraform directory

Open VS Code Integrated Terminal:

```
cd /home/bob/terraform
```

## Step 2: Inspect `main.tf`

```
cat main.tf
```

Find the EC2 resource block for `datacenter-ec2`.

For example:

```
resource "aws_instance" "datacenter_ec2" {
  ami           = "ami-xxxxxxxx"
  instance_type = "t2.micro"

  tags = {
    Name = "datacenter-ec2"
  }
}
```

Keep this resource block unchanged.

## Step 3: Identify the Terraform resource address

Run:

```
terraform init
terraform state list
```

You may see something like:

```
aws_instance.datacenter_ec2
```

Inspect the resource:

```
terraform state show aws_instance.datacenter_ec2
```

Confirm that its Name tag is `datacenter-ec2` and its region is `us-east-1`.

## Step 4: Destroy only the EC2 instance

Run:

```
terraform destroy -target=aws_instance.datacenter_ec2
```

If your resource address is different, replace it with the exact address from `terraform state list`.

Type `yes` when prompted.

This will:

- Terminate the existing EC2 instance.
- Remove the instance from Terraform state.
- Keep the resource definition in `main.tf`.

## Step 5: Verify termination

Get the instance ID:

```
aws ec2 describe-instances \
  --region us-east-1 \
  --filters "Name=tag:Name,Values=datacenter-ec2" \
  --query "Reservations[].Instances[].[InstanceId,State.Name]" \
  --output table
```

Expected state:

```
-------------------------
|   DescribeInstances   |
+-----------------------+
|  i-xxxxxxxxxxxx | terminated |
+-----------------------+
```

If the instance does not appear because AWS has stopped returning the terminated instance, use its previously recorded instance ID:

```
aws ec2 describe-instances \
  --region us-east-1 \
  --instance-ids i-xxxxxxxxxxxx \
  --query "Reservations[].Instances[].State.Name" \
  --output text
```

Expected:

```
terminated
```

## Important: Understand the Terraform behavior

| Operation                   | Result                                              |
| --------------------------- | --------------------------------------------------- |
| Delete resource block       | Provisioning code lost                              |
| `terraform destroy -target` | Instance terminated, code retained                  |
| `terraform state rm`        | Removes state tracking only; does not terminate EC2 |



Correct approach

&#x20;Use targeted Terraform destroy.



Final reminder: Do not run a normal `terraform apply` after destroying the instance. Since the resource block remains in `main.tf`, Terraform will plan to create the EC2 instance again.
