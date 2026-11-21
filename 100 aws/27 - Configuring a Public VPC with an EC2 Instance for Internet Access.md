# 27: Configuring a Public VPC with an EC2 Instance for Internet Access

The Nautilus DevOps Team has received a request from the Networking Team to set up a new public VPC to support a set of public-facing services. This VPC will host various resources that need to be accessible over the internet. As part of this setup, you need to ensure the VPC has public subnets with automatic IP assignment for resources. Additionally, a new EC2 instance will be launched within this VPC to host public applications that require SSH access. This setup will enable the Networking Team to deploy and manage public-facing applications.

Create a public VPC named nautilus-pub-vpc, and a subnet named nautilus-pub-subnet under the same, make sure public IP is being auto assigned to resources under this subnet. Further, create an EC2 instance named nautilus-pub-ec2 under this VPC with instance type t2.micro. Make sure SSH port 22 is open for this instance and accessible over the internet.


Use below given AWS Credentials: (You can run the showcreds command on aws-client host to retrieve these credentials)

Console URL	https://924012702966.signin.aws.amazon.com/console?region=us-east-1
Username	kk_labs_user_794775
Password	OyOQBOSTxJ6N
Start Time	Sat Nov 21 13:04:34 UTC 2026
End Time	Sat Nov 21 14:04:34 UTC 2026

----

For this Nautilus networking lab, you need to create a complete public network setup in **AWS us-east-1**:

1. Public VPC: `nautilus-pub-vpc`
2. Public subnet: `nautilus-pub-subnet`
3. Enable automatic public IPv4 assignment.
4. Internet Gateway attached to the VPC.
5. Public route table with a route to the Internet Gateway.
6. EC2 instance: `nautilus-pub-ec2`, type `t2.micro`.
7. Security group allowing SSH (TCP 22) from the internet.

**Important:** A public subnet needs both automatic public IP assignment and a route to an Internet Gateway. Enabling public IP assignment alone is not sufficient.

## **Step 1: Get AWS credentials**

On the `aws-client` host, run:

```
showcreds
```

Set the region:

```
export AWS_DEFAULT_REGION=us-east-1
```

## **Step 2: Create the VPC**

```
VPC_ID=$(aws ec2 create-vpc \
  --cidr-block 10.0.0.0/16 \
  --query 'Vpc.VpcId' \
  --output text)

aws ec2 create-tags \
  --resources "$VPC_ID" \
  --tags Key=Name,Value=nautilus-pub-vpc

aws ec2 modify-vpc-attribute \
  --vpc-id "$VPC_ID" \
  --enable-dns-support '{"Value":true}'

aws ec2 modify-vpc-attribute \
  --vpc-id "$VPC_ID" \
  --enable-dns-hostnames '{"Value":true}'

echo "VPC ID: $VPC_ID"
```

## **Step 3: Create the public subnet**

```
SUBNET_ID=$(aws ec2 create-subnet \
  --vpc-id "$VPC_ID" \
  --cidr-block 10.0.1.0/24 \
  --availability-zone us-east-1a \
  --query 'Subnet.SubnetId' \
  --output text)
```

Assign the subnet name:

```
aws ec2 create-tags \
  --resources "$SUBNET_ID" \
  --tags Key=Name,Value=nautilus-pub-subnet
```

Enable automatic public IP assignment:

```
aws ec2 modify-subnet-attribute \
  --subnet-id "$SUBNET_ID" \
  --map-public-ip-on-launch
```

## **Step 4: Create and attach Internet Gateway**

Create an Internet Gateway:

```
IGW_ID=$(aws ec2 create-internet-gateway \
  --query 'InternetGateway.InternetGatewayId' \
  --output text)
```

Tag it:

```
aws ec2 create-tags \
  --resources "$IGW_ID" \
  --tags Key=Name,Value=nautilus-pub-igw
```

Attach it to the VPC:

```
aws ec2 attach-internet-gateway \
  --internet-gateway-id "$IGW_ID" \
  --vpc-id "$VPC_ID"
```

## **Step 5: Create public route table**

Create a route table:

```
RT_ID=$(aws ec2 create-route-table \
  --vpc-id "$VPC_ID" \
  --query 'RouteTable.RouteTableId' \
  --output text)
```

Tag it:

```
aws ec2 create-tags \
  --resources "$RT_ID" \
  --tags Key=Name,Value=nautilus-pub-rt
```

Create the internet route:

```
aws ec2 create-route \
  --route-table-id "$RT_ID" \
  --destination-cidr-block 0.0.0.0/0 \
  --gateway-id "$IGW_ID"
```

Associate the route table with the public subnet:

```
aws ec2 associate-route-table \
  --route-table-id "$RT_ID" \
  --subnet-id "$SUBNET_ID"
```

## **Step 6: Create security group for SSH**

```
SG_ID=$(aws ec2 create-security-group \
  --group-name nautilus-pub-sg \
  --description "Allow SSH from internet" \
  --vpc-id "$VPC_ID" \
  --query 'GroupId' \
  --output text)
```

Allow SSH access:

```
aws ec2 authorize-security-group-ingress \
  --group-id "$SG_ID" \
  --protocol tcp \
  --port 22 \
  --cidr 0.0.0.0/0
```

This allows SSH connections from any IPv4 address, as requested.

## **Step 7: Find Ubuntu AMI**

```
AMI_ID=$(aws ec2 describe-images \
  --owners 099720109477 \
  --filters \
    "Name=name,Values=ubuntu/images/hvm-ssd-gp3/ubuntu-noble-24.04-amd64-server-*" \
    "Name=state,Values=available" \
  --query 'Images | sort_by(@, &CreationDate)[-1].ImageId' \
  --output text)

echo "AMI ID: $AMI_ID"
```

## **Step 8: Launch EC2 instance**

```
INSTANCE_ID=$(aws ec2 run-instances \
  --image-id "$AMI_ID" \
  --instance-type t2.micro \
  --subnet-id "$SUBNET_ID" \
  --security-group-ids "$SG_ID" \
  --associate-public-ip-address \
  --tag-specifications \
  'ResourceType=instance,Tags=[{Key=Name,Value=nautilus-pub-ec2}]' \
  --query 'Instances[0].InstanceId' \
  --output text)

echo "Instance ID: $INSTANCE_ID"
```

Wait until the instance is running:

```
aws ec2 wait instance-running \
  --instance-ids "$INSTANCE_ID"
```

## **Step 9: Verify everything**

**Verify VPC:**

```
aws ec2 describe-vpcs \
  --vpc-ids "$VPC_ID" \
  --query 'Vpcs[].{VpcId:VpcId,CIDR:CidrBlock,State:State}'
```

**Verify subnet and automatic public IP assignment:**

```
aws ec2 describe-subnets \
  --subnet-ids "$SUBNET_ID" \
  --query 'Subnets[].{SubnetId:SubnetId,Name:Tags[?Key==`Name`].Value|[0],PublicIPAutoAssign:MapPublicIpOnLaunch}'
```

Expected:

```
PublicIPAutoAssign: true
```

**Verify Internet Gateway:**

```
aws ec2 describe-internet-gateways \
  --internet-gateway-ids "$IGW_ID"
```

**Verify public route:**

```
aws ec2 describe-route-tables \
  --route-table-ids "$RT_ID" \
  --query 'RouteTables[].Routes'
```

You should see:

```
DestinationCidrBlock: 0.0.0.0/0
GatewayId: igw-xxxxxxxx
State: active
```

**Verify EC2 instance:**

```
aws ec2 describe-instances \
  --instance-ids "$INSTANCE_ID" \
  --query 'Reservations[].Instances[].{Name:Tags[?Key==`Name`].Value|[0],InstanceId:InstanceId,InstanceType:InstanceType,PublicIP:PublicIpAddress,State:State.Name}'
```

## **Final architecture**

**Internet**

0.0.0.0/0

**Internet Gateway**

nautilus-pub-igw

Public route: 0.0.0.0/0

**nautilus-pub-vpc**

VPC

**nautilus-pub-subnet**

Public

10.0.1.0/24 · Auto-assign public IPv4 enabled

**nautilus-pub-ec2**

t2.micro · Ubuntu

SSH TCP 22 allowed

## **Completion checklist**

- VPC named `nautilus-pub-vpc`
- Subnet named `nautilus-pub-subnet`
- Auto-assign public IPv4 enabled
- Internet Gateway attached
- Public route table configured
- EC2 named `nautilus-pub-ec2`
- Instance type `t2.micro`
- SSH port 22 open to the internet

**Note:** Opening port 22 makes SSH network access possible, but actual login also requires an EC2 key pair and its private key. For production, restrict SSH to trusted IP addresses rather than the entire internet.

Run the commands in order. If you encounter an error, share the command and its output so we can fix it.
