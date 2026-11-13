# 7: Change EC2 Instance Type
During the migration process, the Nautilus DevOps team created several EC2 instances in different regions. They are currently in the process of identifying the correct resources and utilization and are making continuous changes to ensure optimal resource utilization. Recently, they discovered that one of the EC2 instances was underutilized, prompting them to decide to change the instance type. Please make sure the Status check is completed (if its still in Initializing state) before making any changes to the instance.

1) Change the instance type from t2.micro to t2.nano for datacenter-ec2 instance.

2) Make sure the ec2 instance datacenter-ec2 is in running state after the change.

Use below given AWS Credentials: (You can run the showcreds command on aws-client host to retrieve these credentials)

Console URL	https://919171157955.signin.aws.amazon.com/console?region=us-east-1
Username	kk_labs_user_406957
Password	mg2OufF^wbLt
Start Time	Fri Nov 13 17:29:21 UTC 2026
End Time	Fri Nov 13 18:29:21 UTC 2026

Notes:

Create the resources only in us-east-1 region.

---
You can change the EC2 instance type through the **AWS Management Console (Web UI)**.

Follow these steps carefully.

### **Step 1: Open EC2**

1. Log in to the [AWS Management Console](https://console.aws.amazon.com/ec2/?utm_source=chatgpt.com).
2. Navigate to **EC2 → Instances → Instances**.
3. Select the correct AWS region where `datacenter-ec2` exists.
4. Search for the instance named `datacenter-ec2`.

### **Step 2: Check Status Checks**

1. Select `datacenter-ec2`.
2. Open the **Status checks** tab.
3. Wait until both checks show passed:

- System reachability check: Passed
- Instance reachability check: Passed

[Resource Groups and Tagging for AWS | AWS News Blog](https://images.openai.com/static-rsc-4/YlCZT0WonpA8cWcmbfebhhCci77bo-9oTympWb4TyZ-mYcq1uRwEUSklCuLx4LqbPe3v-uXqTJ-yQYC0JJST4IFMJGtZYzjKRgti3UT_7Tqqmlc13p-bAdVo15LyXbWYJIDUltpkWvEdBcdJ3TWrZNBaycEs02EvPWn48oq0y0U?purpose=inline)

[aws.amazon.com](https://aws.amazon.com/blogs/aws/resource-groups-and-tagging/?utm_source=chatgpt.com)

**Important:** If status checks show `Initializing`, wait until they complete before proceeding.

### **Step 3: Stop the Instance**

1. Select `datacenter-ec2`.
2. Click **Instance state → Stop instance**.
3. Confirm by clicking **Stop**.
4. Wait until the instance state becomes `Stopped`.

### **Step 4: Change Instance Type**

1. Keep the instance selected.
2. Click **Actions → Instance settings → Change instance type**.
3. Select:

`t2.nano`

4\. Click **Apply**.

[Day 7.Change EC2 Instance Type - DEV Community](https://images.openai.com/static-rsc-4/fl7X9dYMQUZqVZ5VFVXbIzn1u3oTQehoAATZpekD3CJ2g85WDwT8I4xNFncck2qKYrKO6ymQdF7LMH_LFF8aLgpfEvTy-L0uBhQEnHijrZzjL1Rz51BP79OLKTLQCKvBB6WG3zw_DmM70oFeMy98QQybCg72wOWV84ugYwBGnDU?purpose=inline)

[dev.to](https://dev.to/thukhakyawe_cloud/day-7change-ec2-instance-type-34gn?utm_source=chatgpt.com)

### **Step 5: Start the Instance**

1. Select `datacenter-ec2`.
2. Click **Instance state → Start instance**.
3. Wait until the instance state becomes `Running`.

### **Step 6: Verify**

Expected configuration

| Property       | Expected value    |
| -------------- | ----------------- |
| Instance name  | `datacenter-ec2`  |
| Previous type  | `t2.micro`        |
| New type       | `t2.nano`         |
| Instance state | `Running`         |
| Status checks  | 2/2 checks passed |

**Important:** Changing the instance type requires stopping the instance first. Its public IPv4 address may change after restarting unless an Elastic IP is associated with it.
