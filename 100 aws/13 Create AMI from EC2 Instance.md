# 13: Create AMI from EC2 Instance

The Nautilus DevOps team is strategizing the migration of a portion of their infrastructure to the AWS cloud. Recognizing the scale of this undertaking, they have opted to approach the migration in incremental steps rather than as a single massive transition. To achieve this, they have segmented large tasks into smaller, more manageable units. This granular approach enables the team to execute the migration in gradual phases, ensuring smoother implementation and minimizing disruption to ongoing operations. By breaking down the migration into smaller tasks, the Nautilus DevOps team can systematically progress through each stage, allowing for better control, risk mitigation, and optimization of resources throughout the migration process.

For this task, create an AMI from an existing EC2 instance named devops-ec2 with the following requirement:

Name of the AMI should be devops-ec2-ami, make sure AMI is in available state.

---
Follow these steps to create an AMI from the existing EC2 instance.

### **Step 1: Open EC2**

1. Log in to the AWS Management Console.
2. Select region **US East (N. Virginia) – us-east-1** (if that is where the instance exists).
3. Navigate to **EC2 → Instances**.

### **Step 2: Create AMI**

1. Search for the instance `devops-ec2`.
2. Select the instance.
3. Click **Actions → Image and templates → Create image**.
4. Enter the following:

| Field             | Value                         |
| ----------------- | ----------------------------- |
| Image name        | `devops-ec2-ami`              |
| Image description | `AMI created from devops-ec2` |
| Reboot instance   | Leave default                 |

5\. Click **Create image**.

### **Step 3: Verify AMI status**

1. Navigate to **EC2 → AMIs** under Images.
2. Search for `devops-ec2-ami`.
3. Refresh until the status changes.

Required state: available

| Property        | Expected value   |
| --------------- | ---------------- |
| Source instance | `devops-ec2`     |
| AMI name        | `devops-ec2-ami` |
| AMI state       | `available`      |

### **AWS CLI alternative**

```
aws ec2 create-image \
  --instance-id <instance-id> \
  --name "devops-ec2-ami" \
  --description "AMI created from devops-ec2" \
  --region us-east-1
```

Check AMI status:

```
aws ec2 describe-images \
  --owners self \
  --filters "Name=name,Values=devops-ec2-ami" \
  --region us-east-1 \
  --query 'Images[*].[Name,State]'
```

**Important:** AMI creation is asynchronous. Wait until the state becomes `available` before submitting the task.
