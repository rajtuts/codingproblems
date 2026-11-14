# 15: Create Volume Snapshot

The Nautilus DevOps team has some volumes in different regions in their AWS account. They are going to setup some automated backups so that all important data can be backed up on regular basis. For now they shared some requirements to take a snapshot of one of the volumes they have.

Create a snapshot of an existing volume named `devops-vol` in `us-east-1` region.

1\) The name of the snapshot must be `devops-vol-ss`.

2\) The description must be `devops Snapshot`.

3\) Make sure the snapshot status is `completed` before submitting the task.\

---
Follow these steps to create an EBS snapshot of the existing volume.

### **Step 1: Open EC2 Volumes**

1. Log in to the AWS Management Console.
2. Select region **US East (N. Virginia) – us-east-1**.
3. Navigate to **EC2 → Elastic Block Store → Volumes**.
4. Search for the volume `devops-vol`.

### **Step 2: Create Snapshot**

1. Select `devops-vol`.
2. Click **Actions → Create snapshot**.
3. Enter the following details:

| Field                     | Value             |
| ------------------------- | ----------------- |
| Volume                    | `devops-vol`      |
| Snapshot name (Tag: Name) | `devops-vol-ss`   |
| Description               | `devops Snapshot` |

4\. Click **Create snapshot**.

### **Step 3: Verify Snapshot Status**

1. Navigate to **EC2 → Elastic Block Store → Snapshots**.
2. Search for `devops-vol-ss`.
3. Refresh until the snapshot status becomes `Completed`.

Required status: completed

### **AWS CLI alternative**

First, get the volume ID:

```
aws ec2 describe-volumes \
  --filters Name=tag:Name,Values=devops-vol \
  --region us-east-1 \
  --query 'Volumes[*].VolumeId' \
  --output text
```

Create the snapshot:

```
aws ec2 create-snapshot \
  --volume-id <volume-id> \
  --description "devops Snapshot" \
  --tag-specifications 'ResourceType=snapshot,Tags=[{Key=Name,Value=devops-vol-ss}]' \
  --region us-east-1
```

Check the status:

```
aws ec2 describe-snapshots \
  --owner-ids self \
  --filters Name=tag:Name,Values=devops-vol-ss \
  --region us-east-1 \
  --query 'Snapshots[*].[SnapshotId,Description,State]'
```

**Important:** Snapshot creation is asynchronous. Wait until the state is `completed` before submitting the task.
