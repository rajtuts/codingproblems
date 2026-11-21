# 26: Configuring an EC2 Instance as a Web Server with Nginx

The Nautilus DevOps Team is working on setting up a new web server for a critical application. The team lead has requested you to create an EC2 instance that will serve as a web server using Nginx. This instance will be part of the initial infrastructure setup for the Nautilus project. Ensuring that the server is correctly configured and accessible from the internet is crucial for the upcoming deployment phase.

As a member of the Nautilus DevOps Team, your task is to create an EC2 instance with the following specifications:

Instance Name: The EC2 instance must be named xfusion-ec2.

AMI: Use any available Ubuntu AMI to create this instance.

User Data Script: Configure the instance to run a user data script during its launch. This script should:

Install the Nginx package.
Start the Nginx service.
Security Group: Ensure that the instance allows HTTP traffic on port 80 from the internet.


For this Nautilus AWS lab, create an Ubuntu EC2 instance named `xfusion-ec2`, install Nginx automatically through EC2 User Data, and allow inbound HTTP traffic on port 80 from `0.0.0.0/0`.

The following AWS CLI procedure creates the security group, configures the startup script, and launches the instance in **us-east-1**.

## **Step 1: Get AWS credentials**

On the `aws-client` host:

```
showcreds
```

Set the region:

```
export AWS_DEFAULT_REGION=us-east-1
```

## **Step 2: Find an Ubuntu AMI**

```
AMI_ID=$(aws ec2 describe-images \
  --owners 099720109477 \
  --filters \
    "Name=name,Values=ubuntu/images/hvm-ssd-gp3/ubuntu-noble-24.04-amd64-server-*" \
    "Name=state,Values=available" \
  --query 'Images | sort_by(@, &CreationDate)[-1].ImageId' \
  --output text)

echo $AMI_ID
```

This retrieves the latest available Ubuntu 24.04 AMI.

## **Step 3: Create a security group**

Find the default VPC:

```
VPC_ID=$(aws ec2 describe-vpcs \
  --filters Name=isDefault,Values=true \
  --query 'Vpcs[0].VpcId' \
  --output text)

echo $VPC_ID
```

Create a security group:

```
SG_ID=$(aws ec2 create-security-group \
  --group-name xfusion-nginx-sg \
  --description "Allow HTTP traffic for Nginx web server" \
  --vpc-id "$VPC_ID" \
  --query GroupId \
  --output text)

echo $SG_ID
```

Allow HTTP traffic from the internet:

```
aws ec2 authorize-security-group-ingress \
  --group-id "$SG_ID" \
  --protocol tcp \
  --port 80 \
  --cidr 0.0.0.0/0
```

## **Step 4: Create User Data script**

Create a file named `userdata.sh`:

```
cat > userdata.sh <<'EOF'
#!/bin/bash

apt-get update -y
apt-get install -y nginx

systemctl enable nginx
systemctl start nginx
EOF
```

This script automatically:

1. Updates Ubuntu packages.
2. Installs Nginx.
3. Enables Nginx at system startup.
4. Starts the Nginx service.

## **Step 5: Launch EC2 instance**

Find a default subnet:

```
SUBNET_ID=$(aws ec2 describe-subnets \
  --filters \
    Name=vpc-id,Values="$VPC_ID" \
    Name=default-for-az,Values=true \
  --query 'Subnets[0].SubnetId' \
  --output text)

echo $SUBNET_ID
```

Launch the instance:

```
INSTANCE_ID=$(aws ec2 run-instances \
  --image-id "$AMI_ID" \
  --instance-type t3.micro \
  --subnet-id "$SUBNET_ID" \
  --security-group-ids "$SG_ID" \
  --user-data file://userdata.sh \
  --tag-specifications \
  'ResourceType=instance,Tags=[{Key=Name,Value=xfusion-ec2}]' \
  --query 'Instances[0].InstanceId' \
  --output text)

echo "Instance ID: $INSTANCE_ID"
```

Wait for the instance:

```
aws ec2 wait instance-running \
  --instance-ids "$INSTANCE_ID"
```

## **Step 6: Verify EC2 instance**

```
aws ec2 describe-instances \
  --instance-ids "$INSTANCE_ID" \
  --query 'Reservations[].Instances[].{InstanceId:InstanceId,Name:Tags[?Key==`Name`].Value|[0],State:State.Name,PublicIP:PublicIpAddress}'
```

## **Step 7: Verify Nginx**

Get the public IP:

```
PUBLIC_IP=$(aws ec2 describe-instances \
  --instance-ids "$INSTANCE_ID" \
  --query 'Reservations[0].Instances[0].PublicIpAddress' \
  --output text)

echo $PUBLIC_IP
```

Wait for cloud-init to finish, then test:

```
curl http://$PUBLIC_IP
```

You should receive the default Nginx welcome page HTML.

[Beginner’s Guide to NGINX Configuration Files | by Raj Matariya | Adrixus | Medium](https://images.openai.com/static-rsc-4/hGFcF1ZpkT8F1ocX1dIGwqbUnoEbpbGaoeQMDXBLtmCx8f206GHA2_JfMVvkfnS9nLEOskzPMgN22pLU0RCH0TgWYbIIAbFgqJJQuSYvHlwa5L3DP2Ptakncy0asDyvvR5lDcTUZvTJFwSBVmNZrvjI4rfcb4PF6IjhhdVOrss8?purpose=inline)

[medium.com](https://medium.com/adrixus/beginners-guide-to-nginx-configuration-files-527fcd6d5efd?utm_source=chatgpt.com)

## **Final expected configuration**

| Requirement      | Expected value          |
| ---------------- | ----------------------- |
| Instance name    | `xfusion-ec2`           |
| AMI              | Ubuntu 24.04            |
| Instance type    | `t3.micro`              |
| User Data        | Install and start Nginx |
| Security group   | `xfusion-nginx-sg`      |
| Inbound protocol | TCP                     |
| Port             | 80                      |
| Source           | `0.0.0.0/0`             |

Completion checklist

- EC2 instance successfully launched.
- Instance name is `xfusion-ec2`.
- User Data script configured.
- Nginx installed and running.
- Security group allows HTTP port 80.
- Public IP available and Nginx accessible.

**Important:** A public IP and a subnet with an internet route through an Internet Gateway are required for internet access. If the default subnet does not automatically assign public IPs, enable public IP assignment or configure the network interface accordingly.

After completing the lab, rotate the AWS password shared in the conversation.
Use below given AWS Credentials: (You can run the showcreds command on aws-client host to retrieve these credentials)

Console URL	https://074952969475.signin.aws.amazon.com/console?region=us-east-1
Username	kk_labs_user_526953
Password	jpkIByp@5@pF
Start Time	Sat Nov 21 12:54:08 UTC 2026
End Time	Sat Nov 21 13:54:08 UTC 2026

----

