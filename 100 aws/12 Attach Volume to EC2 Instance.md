# 12: Attach Volume to EC2 Instance

The Nautilus DevOps team has been creating a couple of services on AWS cloud. They have been breaking down the migration into smaller tasks, allowing for better control, risk mitigation, and optimization of resources throughout the migration process. Recently they came up with requirements mentioned below.

An instance named `datacenter-ec2` and a volume named `datacenter-volume` already exists in `us-east-1` region. Attach the `datacenter-volume` volume to the `datacenter-ec2` instance, make sure to set the device name to `/dev/sdb` while attaching the volume.
---
Follow these steps to attach the EBS volume to the EC2 instance.

### **Step 1: Open EC2**

1. Log in to AWS Management Console.
2. Select region **US East (N. Virginia) – us-east-1**.
3. Navigate to **EC2 → Volumes**.

### **Step 2: Attach the volume**

1. Search for `datacenter-volume`.
2. Select the volume.
3. Click **Actions → Attach volume**.
4. Select the instance `datacenter-ec2`.
5. Set the device name to:
   ```
   /dev/sdb
   ```
6. Click **Attach volume**.

### **Step 3: Verify attachment**

Navigate to **EC2 → Volumes** and refresh.

Expected details:

| Property | Expected value      |
| -------- | ------------------- |
| Volume   | `datacenter-volume` |
| Instance | `datacenter-ec2`    |
| Device   | `/dev/sdb`          |
| State    | `In-use`            |

Expected status: In-use

### **AWS CLI alternative**

```
aws ec2 attach-volume \
  --volume-id <volume-id> \
  --instance-id <instance-id> \
  --device /dev/sdb \
  --region us-east-1
```

Verify:

```
aws ec2 describe-volumes \
  --filters Name=volume-id,Values=<volume-id> \
  --region us-east-1
```

**Important:** The volume and EC2 instance must be in the same Availability Zone.
