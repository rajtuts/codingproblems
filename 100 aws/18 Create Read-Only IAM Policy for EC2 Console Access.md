# 18 Create Read-Only IAM Policy for EC2 Console Access

When establishing infrastructure on the AWS cloud, Identity and Access Management (IAM) is among the first and most critical services to configure. IAM facilitates the creation and management of user accounts, groups, roles, policies, and other access controls. The Nautilus DevOps team is currently in the process of configuring these resources and has outlined the following requirements.

Create an IAM policy named iampolicy_ravi in us-east-1 region, it must allow read-only access to the EC2 console, i.e this policy must allow users to view all instances, AMIs, and snapshots in the Amazon EC2 console.

---

You need to create a **customer-managed IAM policy** named `iampolicy_ravi` with read-only permissions to view EC2 instances, AMIs, and snapshots.

### **Step 1: Open IAM**

1. Log in to AWS Management Console.
2. Search for **IAM**.
3. Navigate to **IAM → Policies**.
4. Click **Create policy**.
5. Select the **JSON** tab.

### **Step 2: Add the policy JSON**

Copy and paste the following policy:

```

{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Action": [
        "ec2:DescribeInstances",
        "ec2:DescribeImages",
        "ec2:DescribeSnapshots"
      ],
      "Resource": "*"
    }
  ]
}

```

**What each permission does:**

| Permission              | Purpose            |
| ----------------------- | ------------------ |
| `ec2:DescribeInstances` | View EC2 instances |
| `ec2:DescribeImages`    | View AMIs          |
| `ec2:DescribeSnapshots` | View EBS snapshots |

`Resource: "*"` allows these read-only actions across all applicable EC2 resources.

### **Step 3: Create policy**

1. Click **Next**.
2. Enter the policy name:

`iampolicy_ravi`

3\. Description (optional):

`Read-only access to EC2 instances, AMIs and snapshots`

4\. Click **Create policy**.

### **Step 4: Verify**

Navigate to **IAM → Policies** and search for `iampolicy_ravi`.

Expected result: Policy created

| Property    | Expected value       |
| ----------- | -------------------- |
| Policy name | `iampolicy_ravi`     |
| Policy type | Customer managed     |
| Effect      | Allow                |
| Permissions | EC2 Describe actions |

**Important:** IAM is a global AWS service. You don't need to select `us-east-1` when creating the policy. The policy can be used to grant access to EC2 resources in that region.

The policy only provides viewing permissions; it does not allow users to create, modify, or delete EC2 resources.
