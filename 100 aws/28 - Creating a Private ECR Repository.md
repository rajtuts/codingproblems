# 28: Creating a Private ECR Repository

The Nautilus DevOps team has been tasked with setting up a containerized application. They need to create a private Amazon Elastic Container Registry (ECR) repository to store their Docker images. Once the repository is created, they will build a Docker image from a Dockerfile located on the aws-client host and push this image to the ECR repository. This process is essential for maintaining and deploying containerized applications in a streamlined manner.

Create a private ECR repository named datacenter-ecr. There is a Dockerfile under /root/pyapp directory on aws-client host, build a docker image using this Dockerfile and push the same to the newly created ECR repo, the image tag must be latest.


Use below given AWS Credentials: (You can run the showcreds command on aws-client host to retrieve these credentials)

Console URL	https://127822742629.signin.aws.amazon.com/console?region=us-east-1
Username	kk_labs_user_219453
Password	bcAtp07hXI47
Start Time	Sat Nov 21 13:13:04 UTC 2026
End Time	Sat Nov 21 14:13:04 UTC 2026**


---

You can complete this using the AWS CLI and Docker on the `aws-client` host. The steps below create the private ECR repository, build the image from `/root/pyapp/Dockerfile`, tag it as `latest`, and push it to ECR.

**Security note:** Avoid sharing AWS passwords in chat. Since these credentials were posted here, rotate the password after completing the lab if possible.

## **1. Configure AWS credentials and region**

On `aws-client`, run:

```
showcreds
export AWS_DEFAULT_REGION=us-east-1
```

Verify the AWS identity:

```
aws sts get-caller-identity
```

## **2. Create the private ECR repository**

```
aws ecr create-repository \
  --repository-name datacenter-ecr \
  --region us-east-1
```

If the repository already exists, continue without recreating it.

Get your AWS account ID and construct the ECR URI:

```
ACCOUNT_ID=$(aws sts get-caller-identity --query Account --output text)
REGION=us-east-1
REPOSITORY=datacenter-ecr
ECR_URI="${ACCOUNT_ID}.dkr.ecr.${REGION}.amazonaws.com/${REPOSITORY}"

echo "$ECR_URI"
```

## **3. Authenticate Docker with ECR**

```
aws ecr get-login-password --region "$REGION" | \
docker login --username AWS --password-stdin \
"${ACCOUNT_ID}.dkr.ecr.${REGION}.amazonaws.com"
```

Expected:

```
Login Succeeded
```

## **4. Build the Docker image**

Go to the application directory and confirm the Dockerfile exists:

```
cd /root/pyapp
ls -l Dockerfile
```

Build the image with the required `latest` tag:

```
docker build -t datacenter-ecr:latest .
```

## **5. Tag the image for ECR**

```
docker tag datacenter-ecr:latest "${ECR_URI}:latest"
```

Verify the tag:

```
docker images | grep datacenter-ecr
```

## **6. Push the image to ECR**

```
docker push "${ECR_URI}:latest"
```

Wait for the push to complete successfully.

## **7. Verify the image in ECR**

```
aws ecr describe-images \
  --repository-name datacenter-ecr \
  --region us-east-1 \
  --query 'imageDetails[].{Tags:imageTags,ImageDigest:imageDigest}' \
  --output table
```

Expected result

The output should show an image with the `latest` tag.

Your final ECR image URI will be:

```
<account-id>.dkr.ecr.us-east-1.amazonaws.com/datacenter-ecr:latest
```

**Completion checklist:**

- Private ECR repository `datacenter-ecr` created.
- Docker image built from `/root/pyapp/Dockerfile`.
- Image tagged `latest`.
- Image pushed successfully.
- ECR image verified with the `latest` tag.
