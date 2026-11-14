# 11: Attach Elastic Network Interface to EC2 Instance

The Nautilus DevOps team has been creating a couple of services on AWS cloud. They have been breaking down the migration into smaller tasks, allowing for better control, risk mitigation, and optimization of resources throughout the migration process. Recently they came up with requirements mentioned below.

An instance named datacenter-ec2 and an elastic network interface named datacenter-eni already exists in us-east-1 region.

Attach the datacenter-eni network interface to the datacenter-ec2 instance.
Make sure status is attached before submitting the task.
Please make sure instance initialisation has been completed before submitting this task.

---
Follow these steps in the AWS Management Console to attach the ENI.

### **Step 1: Open EC2**

1. Sign in to AWS Console.
2. Select region **US East (N. Virginia) – us-east-1**.
3. Navigate to **EC2 → Network Interfaces**.

### **Step 2: Attach the ENI**

1. Search for `datacenter-eni`.
2. Select the network interface.
3. Click **Actions → Attach**.
4. Select the instance:\
   `datacenter-ec2`
5. Select the appropriate device index (typically `1` for a secondary network interface).
6. Click **Attach**.

### **Step 3: Verify attachment**

Navigate to **EC2 → Network Interfaces** and refresh.

Expected status: attached

Verify these details:

| Property          | Expected value   |
| ----------------- | ---------------- |
| Network interface | `datacenter-eni` |
| Instance          | `datacenter-ec2` |
| Region            | `us-east-1`      |
| Status            | `attached`       |

### **AWS CLI alternative**

If you prefer AWS CLI:

```
aws ec2 attach-network-interface \
  --network-interface-id <eni-id> \
  --instance-id <instance-id> \
  --device-index 1 \
  --region us-east-1
```

Check the attachment:

```
aws ec2 describe-network-interfaces \
  --filters Name=network-interface-id,Values=<eni-id> \
  --region us-east-1 \
  --query 'NetworkInterfaces[0].Status'
```

Expected output:

```
" in-use "
```

**Important:** AWS reports the ENI status as `in-use` in the CLI after successful attachment. The console displays the attachment status as `Attached`.

Ensure the EC2 instance has completed initialization before attaching.
