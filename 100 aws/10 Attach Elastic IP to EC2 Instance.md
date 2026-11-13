# 10: Attach Elastic IP to EC2 Instance

The Nautilus DevOps team has been creating a couple of services on AWS cloud. They have been breaking down the migration into smaller tasks, allowing for better control, risk mitigation, and optimization of resources throughout the migration process. Recently they came up with requirements mentioned below.

There is an instance named xfusion-ec2 and an elastic-ip named xfusion-ec2-eip in us-east-1 region. Attach the xfusion-ec2-eip elastic-ip to the xfusion-ec2 instance.

---

Follow these steps to attach the Elastic IP `xfusion-ec2-eip` to the EC2 instance `xfusion-ec2` using the **AWS Management Console (Web UI)**.

### **Step 1: Open EC2**

1. Log in to the [AWS Console – us-east-1](https://console.aws.amazon.com/ec2/home?region=us-east-1\&utm_source=chatgpt.com).
2. Ensure the region is **US East (N. Virginia) – us-east-1**.

### **Step 2: Locate the Elastic IP**

1. In the EC2 dashboard, navigate to **Network & Security → Elastic IPs**.
2. Find `xfusion-ec2-eip`.
3. Select the Elastic IP.

[Creating and Running a Jamulus Server in Amazon EC2— A Practical Guide | by Jim Lamb | Medium](https://images.openai.com/static-rsc-4/c6HVip8we6lBXDQcwQJx0Qp_f_-SxHHywu9IKwsAJosdFEc19DI2gZkdwrAAmeTkGNANZVzZNT9yifeIylGrH9uFMAZ0W4Xu4ph7fJgHvr0tZyYBeGnZNZJ_FT3keFTU5mrxH2WTFhmEW9mSrU1odgFD0Z46dNsytAuBrAmFYKw?purpose=inline)

[jal25.medium.com](https://jal25.medium.com/creating-and-running-a-jamulus-server-in-amazon-ec2-a-practical-guide-c7c1305e0f93?utm_source=chatgpt.com)

### **Step 3: Associate Elastic IP**

1. Click **Actions → Associate Elastic IP address**.
2. Configure the following:

| Field               | Value                                                          |
| ------------------- | -------------------------------------------------------------- |
| Resource type       | Instance                                                       |
| Instance            | `xfusion-ec2`                                                  |
| Private IP address  | Select the available private IP                                |
| Allow reassociation | Enable only if already associated and reassignment is intended |

3\. Click **Associate**.

[How to set up Elastic IP address on AWS](https://images.openai.com/static-rsc-4/x92m0Zn3VD0cf_meTG1mqbknTFYpVdLZPw9JcegAWC2K598Ouq_jQuhC-J1VTf91UUgl6D-zkarjM9nkkuKGf6yML8h5NzqUhIGcH3stwCvbvKi29EyBENL4cZ_DiYSPV0_zEhKMLXWffV8Es5Nr-HNn7xjZmYdMX8jRwzRTCYo?purpose=inline)

[callaba.io](https://callaba.io/how-to-set-up-elastic-ip-address-on-aws?utm_source=chatgpt.com)

### **Step 4: Verify**

Navigate back to **Elastic IPs** and verify:

Expected result

| Property            | Expected value    |
| ------------------- | ----------------- |
| Elastic IP name     | `xfusion-ec2-eip` |
| Associated instance | `xfusion-ec2`     |
| Region              | `us-east-1`       |
| Association state   | Associated        |

**Done!** The Elastic IP is now associated with your EC2 instance.
