# 16 Create IAM User

When establishing infrastructure on the AWS cloud, Identity and Access Management (IAM) is among the first and most critical services to configure. IAM facilitates the creation and management of user accounts, groups, roles, policies, and other access controls. The Nautilus DevOps team is currently in the process of configuring these resources and has outlined the following requirements:

For this task, create an IAM user named `iamuser_jim`.\
---
Follow these steps to create the IAM user `iamuser_jim` in AWS.

### **Step 1: Open IAM**

1. Log in to AWS Management Console.
2. Search for **IAM** in the AWS console.
3. Open **IAM → Users**.
4. Click **Create user**.

### **Step 2: Enter user details**

Enter the following:

| Field     | Value         |
| --------- | ------------- |
| User name | `iamuser_jim` |

Click **Next**.

### **Step 3: Set permissions**

For this task, no permissions are specified.

- Select **Add user to group**, **Copy permissions**, or **Attach policies directly** only if required.
- Otherwise, leave permissions unassigned.

Click **Next**.

### **Step 4: Review and create**

1. Verify the username is `iamuser_jim`.
2. Click **Create user**.

Expected result: User created

### **Step 5: Verify**

Navigate to **IAM → Users**.

You should see:

| Property    | Expected value |
| ----------- | -------------- |
| User name   | `iamuser_jim`  |
| User status | Active         |

**Note:** IAM is a global AWS service. You do not need to select a specific AWS region.
