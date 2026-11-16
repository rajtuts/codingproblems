# 23: Data Migration Between S3 Buckets Using AWS CLI

As part of a data migration project, the team lead has tasked the team with migrating data from an existing S3 bucket to a new S3 bucket. The existing bucket contains a substantial amount of data that must be accurately transferred to the new bucket. The team is responsible for creating the new S3 bucket and ensuring that all data from the existing bucket is copied or synced to the new bucket completely and accurately. It is imperative to perform thorough verification steps to confirm that all data has been successfully transferred to the new bucket without any loss or corruption.

As a member of the Nautilus DevOps Team, your task is to perform the following:

**Create a New Private S3 Bucket:** Name the bucket `xfusion-sync-242343627`.

**Data Migration:** Migrate the entire data from the existing `xfusion-s3-242343627` bucket to the new `xfusion-sync-242343627` bucket.

**Ensure Data Consistency:** Ensure that both buckets have the same data.

**Use AWS CLI:** Use the AWS CLI to perform the creation and data migration tasks.\

---
Execute the following commands on the `aws-client` host.

### **Step 1: Verify AWS credentials**

```
showcreds
aws sts get-caller-identity
```

Set the AWS region:

```
export AWS_DEFAULT_REGION=us-east-1
```

### **Step 2: Create the destination S3 bucket**

```
aws s3api create-bucket \
  --bucket xfusion-sync-242343627 \
  --region us-east-1
```

Ensure the bucket is private:

```
aws s3api put-public-access-block \
  --bucket xfusion-sync-242343627 \
  --public-access-block-configuration \
  BlockPublicAcls=true,IgnorePublicAcls=true,BlockPublicPolicy=true,RestrictPublicBuckets=true
```

### **Step 3: Copy all data from source to destination**

```
aws s3 sync \
  s3://xfusion-s3-242343627 \
  s3://xfusion-sync-242343627
```

Wait until the command completes successfully.

### **Step 4: Verify object counts**

Source:

```
aws s3api list-objects-v2 \
  --bucket xfusion-s3-242343627 \
  --query 'length(Contents[])' \
  --output text
```

Destination:

```
aws s3api list-objects-v2 \
  --bucket xfusion-sync-242343627 \
  --query 'length(Contents[])' \
  --output text
```

The counts should match.

### **Step 5: Verify total data size**

Source:

```
aws s3api list-objects-v2 \
  --bucket xfusion-s3-242343627 \
  --query 'sum(Contents[].Size)' \
  --output text
```

Destination:

```
aws s3api list-objects-v2 \
  --bucket xfusion-sync-242343627 \
  --query 'sum(Contents[].Size)' \
  --output text
```

Both values should match.

### **Step 6: Final synchronization check**

```
aws s3 sync \
  s3://xfusion-s3-242343627 \
  s3://xfusion-sync-242343627 \
  --dryrun
```

If no files are displayed, there are no additional copies identified by the sync comparison.

### **Step 7: Verify destination bucket**

```
aws s3 ls s3://xfusion-sync-242343627 --recursive
```

Expected result

| Requirement        | Expected result          |
| ------------------ | ------------------------ |
| Destination bucket | `xfusion-sync-242343627` |
| Bucket access      | Private                  |
| Source bucket      | `xfusion-s3-242343627`   |
| Data migration     | Completed                |
| Object counts      | Matching                 |
| Total object sizes | Matching                 |
| Final sync         | No pending copies        |

**Note:** For stronger integrity verification, compare object checksums where available. Matching counts and sizes alone do not prove byte-for-byte equality. Also, `aws s3 sync` copies current objects, not historical versions.
