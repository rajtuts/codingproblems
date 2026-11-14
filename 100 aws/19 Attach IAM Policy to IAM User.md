# 19: Attach IAM Policy to IAM User

The Nautilus DevOps team has been creating a couple of services on AWS cloud. They have been breaking down the migration into smaller tasks, allowing for better control, risk mitigation, and optimization of resources throughout the migration process. Recently they came up with requirements mentioned below.

An IAM user named iamuser_mariyam and a policy named iampolicy_mariyam already exist. Attach the IAM policy iampolicy_mariyam to the IAM user iamuser_mariyam.

---

Follow these steps to attach the existing IAM policy to the IAM user.

### **Step 1: Open IAM Users**

1. Log in to the AWS Management Console.
2. Search for **IAM**.
3. Navigate to **IAM → Users**.
4. Select the user `iamuser_mariyam`.

### **Step 2: Attach policy**

1. Open the **Permissions** tab.
2. Click **Add permissions**.
3. Select **Attach policies directly**.
4. Search for `iampolicy_mariyam`.
5. Select the checkbox next to the policy.
6. Click **Next** (if displayed).
7. Click **Add permissions**.

### **Step 3: Verify**

Go to **IAM → Users → iamuser_mariyam → Permissions**.

You should see:

| Property        | Expected value      |
| --------------- | ------------------- |
| IAM user        | `iamuser_mariyam`   |
| Attached policy | `iampolicy_mariyam` |
| Attachment type | Managed policy      |

Expected result: Policy attached

### **AWS CLI alternative**

```
aws iam attach-user-policy \
  --user-name iamuser_mariyam \
  --policy-arn arn:aws:iam::<account-id>:policy/iampolicy_mariyam
```

Verify:

```
aws iam list-attached-user-policies \
  --user-name iamuser_mariyam
```

**Note:** IAM is a global AWS service. No region selection is required.
