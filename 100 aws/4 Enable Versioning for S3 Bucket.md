# 4: Enable Versioning for S3 Bucket

Data protection and recovery are fundamental aspects of data management. It's essential to have systems in place to ensure that data can be recovered in case of accidental deletion or corruption. The DevOps team has received a requirement for implementing such measures for one of the S3 buckets they are managing.

The s3 bucket name is xfusion-s3-339285431, enable versioning for this bucket.\

---
Follow these steps to enable **S3 Bucket Versioning** through the AWS Management Console (Web UI).

### **Step 1: Open S3**

1. Log in to the [AWS Console](https://console.aws.amazon.com/s3/?utm_source=chatgpt.com).
2. Ensure the region is **us-east-1**.
3. Search for **S3** and open the S3 service.

### **Step 2: Select the bucket**

1. Click **Buckets**.
2. Search for:

`xfusion-s3-339285431`

3\. Click the bucket name.

### **Step 3: Enable Versioning**

1. Open the **Properties** tab.
2. Find the **Bucket Versioning** section.
3. Click **Edit**.
4. Select **Enable**.

[Firefly | Understanding State Locking and Versioning in Terraform](https://images.openai.com/static-rsc-4/r_auzmQjxJzIBlfZzFgq5Ncip3XGEimfUL2ObjFghyJad-7ZIzo8EfSYfeSslMEspbNxAlkzgO2Uv5eDWFSEwqmdIuFtKeCss_QFvyn_g7qxrKhYOA6CYrAtRwf3pLxwLM6H4jvdsVwNL5YtibCmn-omJyt4eAA9Uc7atirm_lE?purpose=inline)

[firefly.ai](https://www.firefly.ai/academy/understanding-state-locking-and-versioning-in-terraform?utm_source=chatgpt.com)

5. Click **Save changes**.
6. Confirm if AWS displays a confirmation dialog.

### **Step 4: Verify**

Return to **Properties → Bucket Versioning**.

Expected configuration

| Property   | Value                  |
| ---------- | ---------------------- |
| Bucket     | `xfusion-s3-339285431` |
| Versioning | Enabled                |
| Region     | us-east-1              |

**Result:** S3 versioning is enabled. Future uploads and modifications will retain previous object versions, helping recover accidentally overwritten or deleted objects.
