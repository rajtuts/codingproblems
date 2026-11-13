# 8: Enable Stop Protection for EC2 Instance

As part of the migration, there were some components added to the AWS account. Team created one of the EC2 instances where they need to make some changes now.

There is an EC2 instance named datacenter-ec2 under us-east-1 region, enable the stop protection for this instance.

You can enable **Stop Protection (Disable API Stop Protection)** for the EC2 instance through the AWS Web Console.

### **Step 1: Open EC2**

1. Log in to the [AWS Management Console](https://console.aws.amazon.com/ec2/home?region=us-east-1\&utm_source=chatgpt.com).
2. Ensure the region is **US East (N. Virginia) – us-east-1**.
3. Navigate to **EC2 → Instances → Instances**.

### **Step 2: Select the instance**

1. Search for `datacenter-ec2`.
2. Select the instance.

### **Step 3: Enable Stop Protection**

1. Click **Actions**.
2. Navigate to **Instance settings**.
3. Select **Change stop protection**.
4. Enable the **Stop protection** checkbox.
5. Click **Save**.

[🛡️ AWS 108: Adding a Safety Latch - Enabling EC2 Stop Protection - DEV Community](https://images.openai.com/static-rsc-4/miSVmFkKRE3d8Z3FJxMHc4gVgPxluEM_AibE3RiK6nYSInk4l8GvU8Xipz_f6n5Qxtd7psGjVxpDbhgn43YVcVo5hJoFZwT_ao8gvzZw6BIra4seOtBck51uyT1jK-6g11D-ROIGxLYMGHPPCAzCzMG4yxm7f4rviF52XzWB3Mc?purpose=inline)

[dev.to](https://dev.to/hritikraj8804/aws-108-adding-a-safety-latch-enabling-ec2-stop-protection-4cp7?utm_source=chatgpt.com)

### **Step 4: Verify**

1. Select `datacenter-ec2`.
2. Open the **Details** tab.
3. Find **Stop protection**.

Expected configuration

| Property        | Value            |
| --------------- | ---------------- |
| Instance        | `datacenter-ec2` |
| Region          | `us-east-1`      |
| Stop protection | Enabled          |

**Note:** Stop protection prevents accidental stopping through the EC2 API or console. It does not prevent terminating the instance.
