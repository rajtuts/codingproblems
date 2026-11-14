# 21: Setting Up an EC2 Instance with an Elastic IP for Application Hosting

The Nautilus DevOps Team has received a new request from the Development Team to set up a new EC2 instance. This instance will be used to host a new application that requires a stable IP address. To ensure that the instance has a consistent public IP, an Elastic IP address needs to be associated with it. The instance will be named devops-ec2, and the Elastic IP will be named devops-eip. This setup will help the Development Team to have a reliable and consistent access point for their application.

Create an EC2 instance named devops-ec2 using any linux AMI like ubuntu, the Instance type must be t2.micro and associate an Elastic IP address with this instance, name it as devops-eip.


Use below given AWS Credentials: (You can run the showcreds command on aws-client host to retrieve these credentials)

---

You can complete the entire task using the **AWS Management Console (Web UI)** without using AWS CLI.

## **Step 1: Open EC2**

1. Log in to the AWS Management Console.
2. Select region **US East (N. Virginia) – us-east-1**.
3. Search for **EC2**.
4. Navigate to **EC2 → Instances**.
5. Click **Launch instances**.

## **Step 2: Configure EC2 instance**

[Self host the OSRM API: osrm-backend as a web service](https://images.openai.com/static-rsc-4/phNGCYe7GZII3vJXv7hLwrSn4_HaUL1EmV5ngy-4B1FlVxrV9oy7_khcQ4QmzOTAIiXKRQDi0M5hIXusUsUrhqiNSmCtfUje6dxCyCjc1KNcAfWJ3BMfJpF1oKT89BCinxfS2IgcHEeJH3t_NneKGLI_ByKT4hhuYBvSqnIIyx8?purpose=inline)

[blog.afi.io](https://blog.afi.io/blog/hosting-the-osrm-api-on-amazon-ec2-running-osrm-backend-as-a-web-service/?utm_source=chatgpt.com)

Enter the following details:

| Field                 | Value                                                                   |
| --------------------- | ----------------------------------------------------------------------- |
| Name                  | `devops-ec2`                                                            |
| AMI                   | Ubuntu Server 24.04 LTS                                                 |
| Instance type         | `t2.micro`                                                              |
| Key pair              | Proceed with an existing key pair or create one if SSH access is needed |
| Network               | Default VPC                                                             |
| Auto-assign public IP | Enable                                                                  |

Under Network settings, ensure a suitable subnet is selected.

Click **Launch instance**.

## **Step 3: Wait for initialization**

1. Navigate to **EC2 → Instances**.
2. Search for `devops-ec2`.
3. Wait until:
   - Instance state: `Running`
   - Status checks: `2/2 checks passed`

## **Step 4: Allocate Elastic IP**

[How to set up Elastic IP address on AWS](https://images.openai.com/static-rsc-4/HKUy-ndA_cZAaQ1tYmu8C_fzEun0J9VOOZactz7QCyd72yrjQuVZziyUbc1vKyJt1fhnvUqiBLp9b3l3By_5B7UNoytBNvqvjh5QMDVF5P2lx05fEBZRO7uKsi1t0Srez_RKb_afr2NT8iV-QGPFZZ_0ctH1E1G2dMIElAxaGXI?purpose=inline)

[callaba.io](https://callaba.io/how-to-set-up-elastic-ip-address-on-aws?utm_source=chatgpt.com)

1. Navigate to **EC2 → Network & Security → Elastic IPs**.
2. Click **Allocate Elastic IP address**.
3. Keep the default settings.
4. Click **Allocate**.

## **Step 5: Name the Elastic IP**

1. Select the newly allocated Elastic IP.
2. Choose **Actions → Add/Edit tags**.
3. Click **Add new tag**.

Enter:

| Key  | Value        |
| ---- | ------------ |
| Name | `devops-eip` |

Click **Save**.

## **Step 6: Associate Elastic IP**

[Elastic IP Demo - KodeKloud](https://images.openai.com/static-rsc-4/YUe4DC8TFAgv7m7PkSDQehU9VobMlcAkcGgkzcHGEGv9kDmK-GRTHMONOql1HEnIk4ibqGHlo-T_OWIDrPmYL2ydSgRsI7qMWYff5ZQyvD4ESMVf-ikSlnf17-x0j7k9q3NWz4gUwO1RHMO_itn04lQOJJCv5esgzUEAJvvkCis?purpose=inline)

[notes.kodekloud.com](https://notes.kodekloud.com/docs/AWS-Networking-Fundamentals/Core-Networking-Services/Elastic-IP-Demo/page?utm_source=chatgpt.com)

1. Select the Elastic IP.
2. Click **Actions → Associate Elastic IP address**.
3. Configure:

| Field              | Value                            |
| ------------------ | -------------------------------- |
| Resource type      | Instance                         |
| Instance           | `devops-ec2`                     |
| Private IP address | Select the instance's private IP |

4\. Click **Associate**.

## **Step 7: Final verification**

Go to **EC2 → Instances → devops-ec2**.

Verify:

Expected configuration

| Property               | Expected     |
| ---------------------- | ------------ |
| Instance name          | `devops-ec2` |
| AMI                    | Ubuntu       |
| Instance type          | `t2.micro`   |
| Instance state         | Running      |
| Elastic IP Name tag    | `devops-eip` |
| Elastic IP association | Associated   |

**Important:** The Elastic IP should appear as the instance's public IPv4 address. Also verify the Elastic IP's association details show `devops-ec2`.

---
You can complete this task using the AWS CLI from the `aws-client` host.

### **Step 1: Configure AWS credentials**

If this is the Nautilus lab environment, run:

```
showcreds
```

Configure the credentials using the provided values. Do not share secret keys.

### **Step 2: Set AWS region**

```
aws configure set region us-east-1
```

### **Step 3: Get Ubuntu AMI ID**

```
AMI_ID=$(aws ssm get-parameter \
  --name /aws/service/canonical/ubuntu/server/24.04/stable/current/amd64/hvm/ebs-gp3/ami-id \
  --query 'Parameter.Value' \
  --output text)

echo $AMI_ID
```

### **Step 4: Create EC2 instance**

```
INSTANCE_ID=$(aws ec2 run-instances \
  --image-id "$AMI_ID" \
  --instance-type t2.micro \
  --min-count 1 \
  --max-count 1 \
  --tag-specifications 'ResourceType=instance,Tags=[{Key=Name,Value=devops-ec2}]' \
  --query 'Instances[0].InstanceId' \
  --output text)

echo $INSTANCE_ID
```

### **Step 5: Wait for instance initialization**

```
aws ec2 wait instance-running \
  --instance-ids "$INSTANCE_ID"
```

### **Step 6: Allocate Elastic IP**

```
ALLOCATION_ID=$(aws ec2 allocate-address \
  --domain vpc \
  --tag-specifications 'ResourceType=elastic-ip,Tags=[{Key=Name,Value=devops-eip}]' \
  --query 'AllocationId' \
  --output text)

echo $ALLOCATION_ID
```

### **Step 7: Associate Elastic IP**

```
aws ec2 associate-address \
  --instance-id "$INSTANCE_ID" \
  --allocation-id "$ALLOCATION_ID"
```

### **Step 8: Verify configuration**

Check EC2:

```
aws ec2 describe-instances \
  --instance-ids "$INSTANCE_ID" \
  --query 'Reservations[0].Instances[0].[InstanceId,InstanceType,State.Name,PublicIpAddress]' \
  --output table
```

Check Elastic IP:

```
aws ec2 describe-addresses \
  --allocation-ids "$ALLOCATION_ID" \
  --output table
```

### **Expected result**

Task requirements

| Configuration     | Expected value |
| ----------------- | -------------- |
| Instance name     | `devops-ec2`   |
| AMI               | Ubuntu Linux   |
| Instance type     | `t2.micro`     |
| Instance state    | `running`      |
| Elastic IP name   | `devops-eip`   |
| Elastic IP domain | `vpc`          |
| Association       | `devops-ec2`   |

**Important:**

- Use the same AWS region for the instance and Elastic IP.
- The instance needs a valid subnet and VPC.
- The Elastic IP must show the instance ID under its association details.
- These commands assume the default VPC has an available subnet and your IAM credentials have the required permissions.
