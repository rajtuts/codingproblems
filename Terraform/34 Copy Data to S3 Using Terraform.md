# 34 Copy Data to S3 Using Terraform

The Nautilus DevOps team is presently immersed in data migrations, transferring data from on-premise storage systems to AWS S3 buckets. They have recently received some data that they intend to copy to one of the S3 buckets.

S3 bucket named nautilus-cp-178218314 already exists. Copy the file /tmp/nautilus.txt to s3 bucket nautilus-cp-178218314 using Terraform. The Terraform working directory is /home/bob/terraform. Update the main.tf file (do not create a separate .tf file) to accomplish this task.

Note: Right-click under the EXPLORER section in VS Code and select Open in Integrated Terminal to launch the terminal.

---

You can upload `/tmp/nautilus.txt` to the existing S3 bucket using Terraform's `aws_s3_object` resource.

Follow these steps in VS Code.

### **Step 1: Open Terraform directory**

Open the integrated terminal and execute:

```
cd /home/bob/terraform
```

### **Step 2: Update&#x20;**`main.tf`

Open the existing `main.tf` file and add the following resource. Do not create another `.tf` file.

```
resource "aws_s3_object" "nautilus_upload" {
  bucket = "nautilus-cp-178218314"
  key    = "nautilus.txt"
  source = "/tmp/nautilus.txt"
  etag   = filemd5("/tmp/nautilus.txt")
}
```

**Explanation:**

- `bucket`: Existing S3 bucket name.
- `key`: Object name in S3.
- `source`: Local file path.
- `etag`: Detects changes in the local file and triggers an update.

### **Step 3: Initialize Terraform**

```
terraform init
```

### **Step 4: Validate configuration**

```
terraform validate
```

Expected output:

```
Success! The configuration is valid.
```

### **Step 5: Preview changes**

```
terraform plan
```

Verify that Terraform plans to create `aws_s3_object.nautilus_upload`.

### **Step 6: Upload the file**

```
terraform apply
```

Enter `yes` when prompted.

### **Step 7: Verify upload**

Run:

```
aws s3 ls s3://nautilus-cp-178218314/
```

Expected object:

```
nautilus.txt
```

Expected result

The file `/tmp/nautilus.txt` should now be uploaded to:

```
s3://nautilus-cp-178218314/nautilus.txt
```

**Important:** Keep the resource in `main.tf`. Terraform will track the uploaded S3 object in its state file.
