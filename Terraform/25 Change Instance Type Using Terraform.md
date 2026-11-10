# 24 Change Instance Type Using Terraform
During the migration process, the Nautilus DevOps team created several EC2 instances in different regions. They are currently in the process of identifying the correct resources and utilization and are making continuous changes to ensure optimal resource utilization. Recently, they discovered that one of the EC2 instances was underutilized, prompting them to decide to change the instance type. Please make sure the Status check is completed (if it's still in Initializing state) before making any changes to the instance.

Change the instance type from t2.micro to t2.nano for datacenter-ec2 instance using terraform.

Make sure the EC2 instance datacenter-ec2 is in running state after the change.

The Terraform working directory is /home/bob/terraform. Update the main.tf file (do not create a separate .tf file) to change the instance type.

Note: Right-click under the EXPLORER section in VS Code and select Open in Integrated Terminal to launch the terminal.

---

We need to change the EC2 instance type of `datacenter-ec2` from `t2.micro` to `t2.nano`, while preserving the existing instance.

The important requirement is to wait until both EC2 status checks have completed successfully before stopping or modifying the instance.

We will use Terraform to update `main.tf` and AWS CLI to verify the instance status.

## 1. Navigate to the Terraform directory

Open VS Code Integrated Terminal:

```
cd /home/bob/terraform
```

## 2. Check EC2 instance status

First, identify the instance ID:

```
aws ec2 describe-instances \
  --filters "Name=tag:Name,Values=datacenter-ec2" \
  --query "Reservations[].Instances[].[InstanceId,InstanceType,State.Name]" \
  --output table
```

Make sure the AWS CLI is configured for the same region as the Terraform provider.

Now check the status checks:

```
aws ec2 describe-instance-status \
  --include-all-instances \
  --filters "Name=tag:Name,Values=datacenter-ec2"
```

You need to see:

- System status: `ok`
- Instance status: `ok`

If either status check is `initializing`, wait before proceeding.

## 3. Update `main.tf`

Open the existing `main.tf` file.

Find the existing EC2 resource block:

```
resource "aws_instance" "datacenter_ec2" {
  # Existing configuration

  instance_type = "t2.micro"

  # Other existing settings
}
```

Change only this line:

```
instance_type = "t2.nano"
```

Important: Do not remove the existing AMI, subnet, security group, key pair, or other configuration. We want to modify the existing EC2 instance, not create a new one.

Your updated resource should contain:

```
resource "aws_instance" "datacenter_ec2" {
  # Keep all existing configuration

  instance_type = "t2.nano"
}
```

## 4. Initialize and validate Terraform

```
terraform init
```

```
terraform validate
```

Check the execution plan:

```
terraform plan
```

Expected change:

```
~ resource "aws_instance" "datacenter_ec2" {
    ~ instance_type = "t2.micro" -> "t2.nano"
}
```

The `~` indicates an in-place update.

If you see `-/+`, meaning destroy and recreate, stop and investigate before applying.

## 5. Apply the change

```
terraform apply -auto-approve
```

Terraform and the AWS provider will handle the required stop/start operation to apply the new instance type.

## 6. Verify EC2 instance is running

Retrieve the instance ID:

```
INSTANCE_ID=$(aws ec2 describe-instances \
  --filters "Name=tag:Name,Values=datacenter-ec2" \
  --query "Reservations[0].Instances[0].InstanceId" \
  --output text)
```

Wait until the instance is running:

```
aws ec2 wait instance-running --instance-ids $INSTANCE_ID
```

Check the instance details:

```
aws ec2 describe-instances \
  --instance-ids $INSTANCE_ID \
  --query "Reservations[].Instances[].[InstanceId,InstanceType,State.Name]" \
  --output table
```

Expected result:

EC2 Instance

Running

| Property      | Expected value |
| ------------- | -------------- |
| Name          | datacenter-ec2 |
| Instance type | t2.nano        |
| State         | running        |

## 7. Final Terraform verification

Execute:

```
terraform plan
```

Expected output:

```
No changes.
Your infrastructure matches the configuration.
```

## Complete task checklist

- EC2 status checks completed before modification.
- Existing instance updated from `t2.micro` to `t2.nano`.
- No unnecessary instance recreation.
- EC2 instance is in `running` state.
- Terraform plan reports no changes.
- Only `main.tf` modified.

Important: After restarting, EC2 status checks may temporarily return to `initializing`. That is normal. The instance must be running for task completion; you can also wait until both status checks return `ok`.
