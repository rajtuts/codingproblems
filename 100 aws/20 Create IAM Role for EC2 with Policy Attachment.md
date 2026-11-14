# 20: Create IAM Role for EC2 with Policy Attachment

When establishing infrastructure on the AWS cloud, Identity and Access Management (IAM) is among the first and most critical services to configure. IAM facilitates the creation and management of user accounts, groups, roles, policies, and other access controls. The Nautilus DevOps team is currently in the process of configuring these resources and has outlined the following requirements:

Create an IAM role as below:

1) IAM role name must be iamrole_siva.

2) Entity type must be AWS Service and use case must be EC2.

3) Attach a policy named iampolicy_siva.

---
Follow these steps to create the IAM role `iamrole_siva` and attach the policy `iampolicy_siva`.

### **Step 1: Open IAM Roles**

1. Log in to AWS Management Console.
2. Search for **IAM**.
3. Navigate to **IAM → Roles**.
4. Click **Create role**.

### **Step 2: Select trusted entity**

Choose the following:

| Field               | Value       |
| ------------------- | ----------- |
| Trusted entity type | AWS service |
| Use case            | EC2         |

1. Select **AWS service**.
2. Select **EC2** as the use case.
3. Click **Next**.

### **Step 3: Attach permissions**

1. In the permissions policies search box, search for `iampolicy_siva`.
2. Select the checkbox next to `iampolicy_siva`.
3. Click **Next**.

### **Step 4: Set role name**

Enter:

```
iamrole_siva
```

Click **Create role**.

### **Step 5: Verify**

Navigate to **IAM → Roles → iamrole_siva**.

Expected configuration

| Property        | Expected value   |
| --------------- | ---------------- |
| Role name       | `iamrole_siva`   |
| Trusted entity  | AWS service      |
| Use case        | EC2              |
| Attached policy | `iampolicy_siva` |

### **AWS CLI alternative**

```
aws iam create-role \
  --role-name iamrole_siva \
  --assume-role-policy-document '{
    "Version": "2012-10-17",
    "Statement": [{
      "Effect": "Allow",
      "Principal": {"Service": "ec2.amazonaws.com"},
      "Action": "sts:AssumeRole"
    }]
  }'
```

Attach the policy:

```
aws iam attach-role-policy \
  --role-name iamrole_siva \
  --policy-arn arn:aws:iam::<account-id>:policy/iampolicy_siva
```

**Important:** The IAM policy must already exist. Replace `<account-id>` with your AWS account ID when using the CLI.
