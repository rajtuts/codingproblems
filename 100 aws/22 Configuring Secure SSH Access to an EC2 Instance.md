# 22 Configuring Secure SSH Access to an EC2 Instance

The Nautilus DevOps team needs to set up a new EC2 instance that can be accessed securely from their landing host (`aws-client`). The instance should be of type `t2.micro` and named `datacenter-ec2`. A new SSH key with name `id_rsa` should be created on the `aws-client` host under the`/root/.ssh/` folder, if it doesn't already exist. This key should then be added to the `root` user's authorised keys on the EC2 instance, allowing passwordless SSH access from the `aws-client` host.



Use below given **AWS Credentials:** (You can run the `showcreds` command on `aws-client` host to retrieve these credentials)

| **Console URL** | [**https://320539308557.signin.aws.amazon.com/console?region=us-east-1**](https://320539308557.signin.aws.amazon.com/console?region=us-east-1) |
| --------------- | ---------------------------------------------------------------------------------------------------------------------------------------------- |
| Username        | kk_labs_user_942996                                                                                                                            |
| Password        | ENrdfpw2Q!2D                                                                                                                                   |
| Start Time      | Mon Nov 16 14:10:35 UTC 2026                                                                                                                   |
| End Time        | Mon Nov 16 15:10:35 UTC 2026                                                                                                                   |

---

Can complete this task from the `aws-client` host using AWS CLI.

The approach is to:

1. Create the SSH key pair on `aws-client`.
2. Find the default VPC and subnet.
3. Create a security group allowing SSH from `aws-client`.
4. Launch an Amazon Linux EC2 instance (`t2.micro`).
5. Configure the instance to authorize the `root` user's SSH public key.
6. Verify passwordless SSH access.

## **Step 1: Connect to aws-client**

Open the terminal on `aws-client`.

Check AWS credentials:

```
showcreds
```

Verify AWS CLI access:

```
aws sts get-caller-identity
```

Set the region:

```
export AWS_DEFAULT_REGION=us-east-1
```

## **Step 2: Generate SSH key on aws-client**

Create the SSH directory if it doesn't exist:

```
mkdir -p /root/.ssh
chmod 700 /root/.ssh
```

Generate the key only if it doesn't already exist:

```
if [ ! -f /root/.ssh/id_rsa ]; then
    ssh-keygen -t rsa -b 4096 -f /root/.ssh/id_rsa -N ""
fi
```

Set permissions:

```
chmod 600 /root/.ssh/id_rsa
chmod 644 /root/.ssh/id_rsa.pub
```

Display the public key:

```
cat /root/.ssh/id_rsa.pub
```

## **Step 3: Find the default VPC and subnet**

```
VPC_ID=$(aws ec2 describe-vpcs \
  --filters Name=isDefault,Values=true \
  --query 'Vpcs[0].VpcId' \
  --output text)

echo $VPC_ID
```

Get a default subnet:

```
SUBNET_ID=$(aws ec2 describe-subnets \
  --filters Name=vpc-id,Values=$VPC_ID \
  --query 'Subnets[0].SubnetId' \
  --output text)

echo $SUBNET_ID
```

## **Step 4: Create a security group**

```
SG_ID=$(aws ec2 create-security-group \
  --group-name datacenter-ec2-sg \
  --description "SSH access for datacenter-ec2" \
  --vpc-id $VPC_ID \
  --query GroupId \
  --output text)
```

Allow SSH access from the landing host:

```
MY_IP=$(curl -s https://checkip.amazonaws.com | tr -d '\n')

aws ec2 authorize-security-group-ingress \
  --group-id $SG_ID \
  --protocol tcp \
  --port 22 \
  --cidr ${MY_IP}/32
```

## **Step 5: Get Amazon Linux AMI**

Use the latest Amazon Linux 2023 AMI:

```
AMI_ID=$(aws ssm get-parameter \
  --name /aws/service/ami-amazon-linux-latest/al2023-ami-kernel-default-x86_64 \
  --query 'Parameter.Value' \
  --output text)

echo $AMI_ID
```

## **Step 6: Configure root SSH authorization**

Create the user-data script:

```
PUBKEY=$(cat /root/.ssh/id_rsa.pub)

cat > /tmp/user-data.sh <<EOF
#!/bin/bash

mkdir -p /root/.ssh
chmod 700 /root/.ssh

echo "$PUBKEY" > /root/.ssh/authorized_keys

chmod 600 /root/.ssh/authorized_keys
chown -R root:root /root/.ssh

if grep -q '^PermitRootLogin' /etc/ssh/sshd_config; then
    sed -i 's/^PermitRootLogin.*/PermitRootLogin prohibit-password/' /etc/ssh/sshd_config
else
    echo 'PermitRootLogin prohibit-password' >> /etc/ssh/sshd_config
fi

systemctl restart sshd
EOF
```

This configures key-based root login while keeping root password authentication disabled.

## **Step 7: Launch EC2 instance**

```
INSTANCE_ID=$(aws ec2 run-instances \
  --image-id "$AMI_ID" \
  --instance-type t2.micro \
  --subnet-id "$SUBNET_ID" \
  --security-group-ids "$SG_ID" \
  --associate-public-ip-address \
  --user-data file:///tmp/user-data.sh \
  --tag-specifications 'ResourceType=instance,Tags=[{Key=Name,Value=datacenter-ec2}]' \
  --query 'Instances[0].InstanceId' \
  --output text)

echo $INSTANCE_ID
```

## **Step 8: Wait for EC2 instance**

```
aws ec2 wait instance-running \
  --instance-ids "$INSTANCE_ID"
```

Retrieve its public IP:

```
PUBLIC_IP=$(aws ec2 describe-instances \
  --instance-ids "$INSTANCE_ID" \
  --query 'Reservations[0].Instances[0].PublicIpAddress' \
  --output text)

echo $PUBLIC_IP
```

Wait for SSH initialization before connecting.

## **Step 9: Verify passwordless SSH**

From `aws-client`:

```
ssh -i /root/.ssh/id_rsa \
  -o StrictHostKeyChecking=accept-new \
  root@$PUBLIC_IP
```

If successful, you should see a shell similar to:

```
[root@ip-172-31-xx-xx ~]#
```

Verify the logged-in user:

```
whoami
```

Expected output:

```
root
```



Expected result

&#x20;EC2 instance `datacenter-ec2` is running, and the `root` user can connect using the SSH private key on `aws-client`.



**Important:** These commands are a procedure for you to execute; I haven't directly accessed your AWS account or created the instance. Also, avoid sharing AWS passwords in chat and rotate temporary credentials if they have been exposed beyond their intended lab use.
