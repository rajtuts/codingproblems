# 5 Create GP3 Volume

The Nautilus DevOps team is strategizing the migration of a portion of their infrastructure to the AWS cloud. Recognizing the scale of this undertaking, they have opted to approach the migration in incremental steps rather than as a single massive transition. To achieve this, they have segmented large tasks into smaller, more manageable units. This granular approach enables the team to execute the migration in gradual phases, ensuring smoother implementation and minimizing disruption to ongoing operations. By breaking down the migration into smaller tasks, the Nautilus DevOps team can systematically progress through each stage, allowing for better control, risk mitigation, and optimization of resources throughout the migration process.

Create a volume with the following requirements:

Name of the volume should be datacenter-volume.

Volume type must be gp3.

Volume size must be 2 GiB.

---
Follow these steps to create an **Amazon EBS Volume** using the AWS Management Console (Web UI).

### **Step 1: Open EC2**

1. Log in to the [AWS Console](https://console.aws.amazon.com/ec2/?utm_source=chatgpt.com).
2. Ensure the region is **US East (N. Virginia) – us-east-1**.
3. Navigate to **EC2 → Elastic Block Store → Volumes**.

### **Step 2: Create Volume**

1. Click **Create volume**.
2. Enter the following configuration:

[💾 AWS 105: Persistent Storage Power-Up - Creating an EBS Volume - DEV Community](https://images.openai.com/static-rsc-4/WS7iT0R4wcm69EYRk4i5s98bpk0qxVfyuJLhODnnkRrUewnKkYeXHElS1YQFMpXymOX7Bq71-aJ0YrMJHCUL2NzBRapne-b_9u10-igVqc__eKbSJaZcNBYFkZcdALYsGo74ahD5Evx0xPne4PjOdUhqZx3yhU922YQeHq5g8Wg?purpose=inline)

[dev.to](https://dev.to/hritikraj8804/aws-105-persistent-storage-power-up-creating-an-ebs-volume-2n0g?utm_source=chatgpt.com)

| Field             | Value                            |
| ----------------- | -------------------------------- |
| Volume type       | `gp3`                            |
| Size              | `2 GiB`                          |
| Availability Zone | Select an AZ (e.g. `us-east-1a`) |
| IOPS              | Leave default                    |
| Throughput        | Leave default                    |

### **Step 3: Add Name Tag**

Under **Tags**, click **Add new tag**.

| Key  | Value               |
| ---- | ------------------- |
| Name | `datacenter-volume` |

### **Step 4: Create Volume**

Click **Create volume**.

### **Step 5: Verify**

Navigate to **EC2 → Elastic Block Store → Volumes** and verify:

Expected configuration

| Property    | Value               |
| ----------- | ------------------- |
| Name        | `datacenter-volume` |
| Volume type | `gp3`               |
| Size        | `2 GiB`             |
| State       | `Available`         |

**That's it!** Your EBS volume is created.
