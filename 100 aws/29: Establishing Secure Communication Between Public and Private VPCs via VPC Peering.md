# 29: Establishing Secure Communication Between Public and Private VPCs via VPC Peering

The Nautilus DevOps team has been tasked with demonstrating the use of VPC Peering to enable communication between two VPCs. One VPC will be a private VPC that contains a private EC2 instance, while the other will be the default public VPC containing a publicly accessible EC2 instance.

1) There is already an existing EC2 instance in the public vpc/subnet:

Name: datacenter-public-ec2
2) There is already an existing Private VPC:

Name: datacenter-private-vpc
CIDR: 10.1.0.0/16
3) There is already an existing Subnet in datacenter-private-vpc:

Name: datacenter-private-subnet
CIDR: 10.1.1.0/24
4) There is already an existing EC2 instance in the private subnet:

Name: datacenter-private-ec2
5) Create a Peering Connection between the Default VPC and the Private VPC:

VPC Peering Connection Name: datacenter-vpc-peering
6) Configure Route Tables to enable communication between the two VPCs.

Ensure the private EC2 instance is accessible from the public EC2 instance.
7) Test the Connection:

Add /root/.ssh/id_rsa.pub public key to the public EC2 instance's ec2-user's authorized_keys to make sure we are able to ssh into this instance from AWS client host. You may also need to update the security group of the private EC2 instance to allow ICMP traffic from the public/default VPC CIDR. This will enable you to ping the private instance from the public instance.
SSH into the public EC2 instance and ensure that you can ping the private EC2 instance.

Use below given AWS Credentials: (You can run the showcreds command on aws-client host to retrieve these credentials)

Console URL	https://243552893788.signin.aws.amazon.com/console?region=us-east-1
Username	kk_labs_user_312351
Password	K1e!tlrU^1w!
Start Time	Sat Nov 21 13:19:33 UTC 2026
End Time	Sat Nov 21 14:19:33 UTC 2026


---

You can configure VPC peering between the existing **default VPC** and `datacenter-private-vpc` using AWS CLI. The important steps are to create and accept the peering connection, update both VPC route tables, and allow the required traffic through the private EC2 security group.

We'll use `us-east-1` throughout.

**Security note:** The AWS password was included in your message. Avoid sharing credentials in chat and rotate the password after the lab if possible.

## **1. Configure AWS CLI**

On the `aws-client` host:

```
showcreds
export AWS_DEFAULT_REGION=us-east-1
```

Verify the account:

```
aws sts get-caller-identity
```

## **2. Find the VPC and EC2 details**

Get the default VPC ID:

```
DEFAULT_VPC_ID=$(aws ec2 describe-vpcs \
  --filters Name=isDefault,Values=true \
  --query 'Vpcs[0].VpcId' --output text)

echo "Default VPC: $DEFAULT_VPC_ID"
```

Get the private VPC ID:

```
PRIVATE_VPC_ID=$(aws ec2 describe-vpcs \
  --filters Name=tag:Name,Values=datacenter-private-vpc \
  --query 'Vpcs[0].VpcId' --output text)

echo "Private VPC: $PRIVATE_VPC_ID"
```

Get both EC2 instance details:

```
PUBLIC_INSTANCE_ID=$(aws ec2 describe-instances \
  --filters Name=tag:Name,Values=datacenter-public-ec2 \
            Name=instance-state-name,Values=running \
  --query 'Reservations[0].Instances[0].InstanceId' --output text)

PRIVATE_INSTANCE_ID=$(aws ec2 describe-instances \
  --filters Name=tag:Name,Values=datacenter-private-ec2 \
            Name=instance-state-name,Values=running \
  --query 'Reservations[0].Instances[0].InstanceId' --output text)

echo "Public instance: $PUBLIC_INSTANCE_ID"
echo "Private instance: $PRIVATE_INSTANCE_ID"
```

Get subnet IDs, IP addresses, and security groups:

```
aws ec2 describe-instances \
  --instance-ids "$PUBLIC_INSTANCE_ID" "$PRIVATE_INSTANCE_ID" \
  --query 'Reservations[].Instances[].{Name:Tags[?Key==`Name`].Value|[0],Subnet:SubnetId,PrivateIP:PrivateIpAddress,PublicIP:PublicIpAddress,SecurityGroups:SecurityGroups[].GroupId}' \
  --output table
```

Save the values:

```
PUBLIC_SUBNET_ID=$(aws ec2 describe-instances \
  --instance-ids "$PUBLIC_INSTANCE_ID" \
  --query 'Reservations[0].Instances[0].SubnetId' --output text)

PRIVATE_SUBNET_ID=$(aws ec2 describe-instances \
  --instance-ids "$PRIVATE_INSTANCE_ID" \
  --query 'Reservations[0].Instances[0].SubnetId' --output text)

PRIVATE_IP=$(aws ec2 describe-instances \
  --instance-ids "$PRIVATE_INSTANCE_ID" \
  --query 'Reservations[0].Instances[0].PrivateIpAddress' --output text)

PUBLIC_IP=$(aws ec2 describe-instances \
  --instance-ids "$PUBLIC_INSTANCE_ID" \
  --query 'Reservations[0].Instances[0].PublicIpAddress' --output text)

PRIVATE_SG=$(aws ec2 describe-instances \
  --instance-ids "$PRIVATE_INSTANCE_ID" \
  --query 'Reservations[0].Instances[0].SecurityGroups[0].GroupId' --output text)

echo "Public IP: $PUBLIC_IP"
echo "Private IP: $PRIVATE_IP"
echo "Private SG: $PRIVATE_SG"
```

## **3. Create the VPC peering connection**

Create the peering connection:

```
PEERING_ID=$(aws ec2 create-vpc-peering-connection \
  --vpc-id "$DEFAULT_VPC_ID" \
  --peer-vpc-id "$PRIVATE_VPC_ID" \
  --query 'VpcPeeringConnection.VpcPeeringConnectionId' \
  --output text)

echo "$PEERING_ID"
```

Add the required Name tag:

```
aws ec2 create-tags \
  --resources "$PEERING_ID" \
  --tags Key=Name,Value=datacenter-vpc-peering
```

Accept the peering connection:

```
aws ec2 accept-vpc-peering-connection \
  --vpc-peering-connection-id "$PEERING_ID"
```

Wait briefly and verify:

```
aws ec2 describe-vpc-peering-connections \
  --vpc-peering-connection-ids "$PEERING_ID" \
  --query 'VpcPeeringConnections[].{ID:VpcPeeringConnectionId,Status:Status.Code,Requester:RequesterVpcInfo.VpcId,Accepter:AccepterVpcInfo.VpcId}' \
  --output table
```

Expected status: active

## **4. Find the route tables**

Find the route table associated with the public instance's subnet:

```
PUBLIC_RT=$(aws ec2 describe-route-tables \
  --filters Name=association.subnet-id,Values="$PUBLIC_SUBNET_ID" \
  --query 'RouteTables[0].RouteTableId' --output text)
```

Find the route table associated with the private instance's subnet:

```
PRIVATE_RT=$(aws ec2 describe-route-tables \
  --filters Name=association.subnet-id,Values="$PRIVATE_SUBNET_ID" \
  --query 'RouteTables[0].RouteTableId' --output text)
```

If either result is `None`, that subnet uses its VPC's main route table. Find that route table:

```
if [ "$PUBLIC_RT" = "None" ]; then
  PUBLIC_RT=$(aws ec2 describe-route-tables \
    --filters Name=vpc-id,Values="$DEFAULT_VPC_ID" \
              Name=association.main,Values=true \
    --query 'RouteTables[0].RouteTableId' --output text)
fi

if [ "$PRIVATE_RT" = "None" ]; then
  PRIVATE_RT=$(aws ec2 describe-route-tables \
    --filters Name=vpc-id,Values="$PRIVATE_VPC_ID" \
              Name=association.main,Values=true \
    --query 'RouteTables[0].RouteTableId' --output text)
fi

echo "Public route table: $PUBLIC_RT"
echo "Private route table: $PRIVATE_RT"
```

## **5. Add routes in both VPCs**

First, get the VPC CIDRs:

```
DEFAULT_CIDR=$(aws ec2 describe-vpcs \
  --vpc-ids "$DEFAULT_VPC_ID" \
  --query 'Vpcs[0].CidrBlock' --output text)

PRIVATE_CIDR=$(aws ec2 describe-vpcs \
  --vpc-ids "$PRIVATE_VPC_ID" \
  --query 'Vpcs[0].CidrBlock' --output text)

echo "Default VPC CIDR: $DEFAULT_CIDR"
echo "Private VPC CIDR: $PRIVATE_CIDR"
```

Add a route in the **default/public VPC** to reach the private VPC:

```
aws ec2 create-route \
  --route-table-id "$PUBLIC_RT" \
  --destination-cidr-block "$PRIVATE_CIDR" \
  --vpc-peering-connection-id "$PEERING_ID"
```

Add a route in the **private VPC** to reach the default VPC:

```
aws ec2 create-route \
  --route-table-id "$PRIVATE_RT" \
  --destination-cidr-block "$DEFAULT_CIDR" \
  --vpc-peering-connection-id "$PEERING_ID"
```

If you get `RouteAlreadyExists`, inspect the existing route and update it with `replace-route` if its target is incorrect.

## **6. Update the private EC2 security group**

Allow ICMP (ping) from the default VPC CIDR:

```
aws ec2 authorize-security-group-ingress \
  --group-id "$PRIVATE_SG" \
  --protocol icmp \
  --port -1 \
  --cidr "$DEFAULT_CIDR"
```

If you also want to SSH from the public EC2 instance to the private EC2 instance, allow TCP port 22 from the default VPC CIDR:

```
aws ec2 authorize-security-group-ingress \
  --group-id "$PRIVATE_SG" \
  --protocol tcp \
  --port 22 \
  --cidr "$DEFAULT_CIDR"
```

If a rule already exists, AWS may return a duplicate-permission error; that is okay if the existing rule allows the same traffic.

Also make sure the private instance's network ACL permits the traffic and return traffic. Its operating-system firewall must not block ICMP.

## **7. Configure SSH access from aws-client to the public EC2**

Check that your key files exist:

```
ls -l /root/.ssh/id_rsa /root/.ssh/id_rsa.pub
```

If SSH access to the public instance is already configured, copy your public key:

```
ssh-copy-id -i /root/.ssh/id_rsa.pub ec2-user@"$PUBLIC_IP"
```

If that command cannot authenticate, use an already-authorized access method (for example, EC2 Instance Connect, if enabled and permitted) to add the contents of `/root/.ssh/id_rsa.pub` to:

```
/home/ec2-user/.ssh/authorized_keys
```

Ensure the file and directory permissions are correct:

```
chmod 700 ~/.ssh
chmod 600 ~/.ssh/authorized_keys
```

Then connect from `aws-client`:

```
ssh -i /root/.ssh/id_rsa ec2-user@"$PUBLIC_IP"
```

## **8. Test connectivity from public EC2 to private EC2**

Once logged into the public EC2 instance, run:

```
ping -c 4 PRIVATE_INSTANCE_PRIVATE_IP
```

Replace `PRIVATE_INSTANCE_PRIVATE_IP` with the private IP printed in step 2.

For example, if the private IP is `10.1.1.25`:

```
ping -c 4 10.1.1.25
```

Expected result

You should see ping replies from the private EC2 instance.

## **9. Final verification**

Back on `aws-client`, verify the peering connection:

```
aws ec2 describe-vpc-peering-connections \
  --filters Name=status-code,Values=active \
  --query 'VpcPeeringConnections[].{Name:Tags[?Key==`Name`].Value|[0],ID:VpcPeeringConnectionId,Status:Status.Code}' \
  --output table
```

Verify both route tables:

```
aws ec2 describe-route-tables \
  --route-table-ids "$PUBLIC_RT" "$PRIVATE_RT" \
  --query 'RouteTables[].{RouteTable:RouteTableId,Routes:Routes[].{Destination:DestinationCidrBlock,Peering:VpcPeeringConnectionId,State:State}}' \
  --output json
```

The routes should show:

- Default VPC route table → `10.1.0.0/16` via the peering connection.
- Private VPC route table → default VPC CIDR via the same peering connection.

**Important:** VPC peering is not transitive. This setup enables direct communication between these two VPCs only. If you encounter an error at any step, share the command and its output (redact credentials), and I can help troubleshoot it.
