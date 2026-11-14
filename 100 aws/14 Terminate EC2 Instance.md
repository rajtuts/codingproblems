# 14: Terminate EC2 Instance

During the migration process, several resources were created under the AWS account. Later on, some of these resources became obsolete as alternative solutions were implemented. Similarly, there is an instance that needs to be deleted as it is no longer in use.

1) Delete the ec2 instance named devops-ec2 present in us-east-1 region.

2) Before submitting your task, make sure instance is in terminated state.

--- 
To delete the EC2 instance `devops-ec2` in the `us-east-1` region:

### **Steps using AWS Management Console**

1. Sign in to the **AWS Management Console**.
2. Navigate to **EC2 → Instances**.
3. Select the region **US East (N. Virginia) – us-east-1**.
4. Search for the instance named `devops-ec2`.
5. Select the instance.
6. Click **Instance state → Terminate instance**.
7. Confirm termination.

### **Verify termination**

- Refresh the EC2 Instances page.
- Locate `devops-ec2`.
- Check the **Instance state** column.
- Wait until the state changes to:

Terminated

### **AWS CLI alternative**

```
# Find the instance ID
aws ec2 describe-instances \
  --region us-east-1 \
  --filters "Name=tag:Name,Values=devops-ec2" \
  --query "Reservations[].Instances[].[InstanceId,State.Name]" \
  --output table

# Terminate the instance (replace with actual instance ID)
aws ec2 terminate-instances \
  --region us-east-1 \
  --instance-ids i-xxxxxxxxxxxxxxxxx

# Verify termination
aws ec2 describe-instances \
  --region us-east-1 \
  --instance-ids i-xxxxxxxxxxxxxxxxx \
  --query "Reservations[].Instances[].[InstanceId,State.Name]" \
  --output table
```

**Important:** Submit the task only after the instance reaches the `terminated` state.
