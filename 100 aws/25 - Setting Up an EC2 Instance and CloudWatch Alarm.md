# 25: Setting Up an EC2 Instance and CloudWatch Alarm

The Nautilus DevOps team has been tasked with setting up an EC2 instance for their application. To ensure the application performs optimally, they also need to create a CloudWatch alarm to monitor the instance's CPU utilization. The alarm should trigger if the CPU utilization exceeds 90% for one consecutive 5-minute period. To send notifications, use the SNS topic named xfusion-sns-topic which is already created.

Launch EC2 Instance: Create an EC2 instance named xfusion-ec2 using any appropriate Ubuntu AMI.

Create CloudWatch Alarm: Create a CloudWatch alarm named xfusion-alarm with the following specifications:

Statistic: Average
Metric: CPU Utilization
Threshold: >= 90% for 1 consecutive 5-minute period.
Alarm Actions: Send a notification to xfusion-sns-topic.

Use below given AWS Credentials: (You can run the showcreds command on aws-client host to retrieve these credentials)

Console URL	https://604120469714.signin.aws.amazon.com/console?region=us-east-1
Username	kk_labs_user_722703
Password	3!dwU!@HTY^L
Start Time	Sat Nov 21 12:48:25 UTC 2026
End Time	Sat Nov 21 13:48:25 UTC 2026

---
You need to create two AWS resources in **us-east-1**:

1. EC2 instance: `xfusion-ec2`
2. CloudWatch alarm: `xfusion-alarm`

The alarm must monitor the instance's `CPUUtilization` metric and publish notifications to the existing SNS topic `xfusion-sns-topic`.

I can't directly access your `aws-client` host or AWS console from this chat, but you can complete the task using the AWS CLI commands below.

### **Step 1: Retrieve AWS credentials**

Connect to the `aws-client` host and execute:

```
showcreds
```

Set the credentials in your terminal as instructed by the command. Set the region:

```
export AWS_DEFAULT_REGION=us-east-1
```

### **Step 2: Get the Ubuntu AMI ID**

Use the latest Ubuntu 22.04 LTS AMI:

```
AMI_ID=$(aws ec2 describe-images \
  --owners 099720109477 \
  --filters \
    "Name=name,Values=ubuntu/images/hvm-ssd/ubuntu-jammy-22.04-amd64-server-*" \
    "Name=state,Values=available" \
  --query 'Images | sort_by(@, &CreationDate)[-1].ImageId' \
  --output text)

echo $AMI_ID
```

### **Step 3: Launch EC2 instance**

```
INSTANCE_ID=$(aws ec2 run-instances \
  --image-id "$AMI_ID" \
  --instance-type t3.micro \
  --count 1 \
  --tag-specifications \
  'ResourceType=instance,Tags=[{Key=Name,Value=xfusion-ec2}]' \
  --query 'Instances[0].InstanceId' \
  --output text)

echo "Instance ID: $INSTANCE_ID"
```

Wait until the instance is running:

```
aws ec2 wait instance-running \
  --instance-ids "$INSTANCE_ID"
```

### **Step 4: Get SNS topic ARN**

Retrieve the existing SNS topic ARN:

```
SNS_ARN=$(aws sns list-topics \
  --query "Topics[?ends_with(TopicArn, ':xfusion-sns-topic')].TopicArn | [0]" \
  --output text)

echo "$SNS_ARN"
```

### **Step 5: Create CloudWatch alarm**

Execute:

```
aws cloudwatch put-metric-alarm \
  --alarm-name "xfusion-alarm" \
  --alarm-description "Alarm when EC2 CPU utilization is >= 90% for 5 minutes" \
  --namespace "AWS/EC2" \
  --metric-name "CPUUtilization" \
  --dimensions Name=InstanceId,Value="$INSTANCE_ID" \
  --statistic Average \
  --period 300 \
  --evaluation-periods 1 \
  --threshold 90 \
  --comparison-operator GreaterThanOrEqualToThreshold \
  --alarm-actions "$SNS_ARN"
```

### **Step 6: Verify resources**

**Verify EC2 instance:**

```
aws ec2 describe-instances \
  --instance-ids "$INSTANCE_ID" \
  --query 'Reservations[].Instances[].{Name:Tags[?Key==`Name`].Value|[0],InstanceId:InstanceId,State:State.Name}'
```

**Verify CloudWatch alarm:**

```
aws cloudwatch describe-alarms \
  --alarm-names xfusion-alarm \
  --query 'MetricAlarms[].{Name:AlarmName,Metric:MetricName,Threshold:Threshold,Period:Period,EvaluationPeriods:EvaluationPeriods,Statistic:Statistic,Actions:AlarmActions}'
```

### **Expected configuration**

**EC2 Instance**

Name

`xfusion-ec2`

AMI

Ubuntu 22.04 LTS

Instance type

`t3.micro`

**CloudWatch Alarm**

Alarm name

`xfusion-alarm`

Metric

`CPUUtilization`

Statistic

`Average`

Threshold

`>= 90%`

Period

`300 seconds`

Evaluation periods

`1`

SNS topic

`xfusion-sns-topic`

Required configuration

**Important:** The alarm will enter `INSUFFICIENT_DATA` initially until CloudWatch receives metric data. That is normal.

Also, since credentials were shared in this conversation, rotate the AWS password after completing the lab.

Run these commands and share any errors you encounter. I can help troubleshoot until both resources are verified.
