# 3 Create Subnet
The Nautilus DevOps team is strategizing the migration of a portion of their infrastructure to the AWS cloud. Recognizing the scale of this undertaking, they have opted to approach the migration in incremental steps rather than as a single massive transition.

For this task, create one subnet named datacenter-subnet under default VPC.



Use below given AWS Credentials: (You can run the showcreds command on aws-client host to retrieve these credentials)

Console URL	https://547314607259.signin.aws.amazon.com/console?region=us-east-1
Username	kk_labs_user_876548
Password	GYns%8vcG^Uh
Start Time	Fri Nov 13 13:16:22 UTC 2026
End Time	Fri Nov 13 14:16:22 UTC 2026

Notes:

Create the resources only in us-east-1 region.

---
Follow these steps to create the subnet using the **AWS Management Console (Web UI)**.

### **Step 1: Login to AWS**

1. Open the provided [AWS Console](https://547314607259.signin.aws.amazon.com/console?region=us-east-1\&utm_source=chatgpt.com).
2. Sign in using the provided credentials.
3. Ensure the region is **US East (N. Virginia) – us-east-1**.

### **Step 2: Open VPC**

1. Search for **VPC** in the AWS console search bar.
2. Open **VPC**.
3. From the left navigation pane, select **Subnets**.

### **Step 3: Create subnet**

1. Click **Create subnet**.
2. Enter the following details:

| Field                  | Value                                             |
| ---------------------- | ------------------------------------------------- |
| VPC ID                 | Select Default VPC                                |
| Subnet name            | `datacenter-subnet`                               |
| Availability Zone      | Select any available AZ in us-east-1              |
| IPv4 subnet CIDR block | Choose an unused CIDR within the default VPC CIDR |

[An Illustrated Hands-on Intro to AWS VPC Networking | DevCentral](https://images.openai.com/static-rsc-4/7CSTLNFfAPWWF0WWXp3knjP2aeYJ-52m4G7AWn4VqWnBp4HLxWM2p1oxcacoPRi1RT3R08hXPj9AUU49YHDvVVM-SM4gLNnR8s1la1d9UF6-hGZiGdlvWCUGt2OfzFEXnJ7n_IvkqjYFzGfJbrQ_XgLkfWSgugqgvRpOK-6KU_4?purpose=inline)

[community.f5.com](https://community.f5.com/kb/technicalarticles/an-illustrated-hands-on-intro-to-aws-vpc-networking/281114?utm_source=chatgpt.com)

**Important:** The default VPC typically uses `172.31.0.0/16`. Check existing subnets before selecting an unused CIDR, for example `172.31.100.0/24` if available.

### **Step 4: Create subnet**

1. Click **Create subnet**.
2. Wait for the success notification.

### **Step 5: Verify**

Navigate to **VPC → Subnets** and verify:

| Property    | Expected value      |
| ----------- | ------------------- |
| Subnet name | `datacenter-subnet` |
| VPC         | Default VPC         |
| Region      | us-east-1           |
| State       | Available           |

Task completed

Your subnet should now be available under the default VPC.
