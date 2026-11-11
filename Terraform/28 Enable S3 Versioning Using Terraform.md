# 28 Enable S3 Versioning Using Terraform

Data protection and recovery are fundamental aspects of data management. It's essential to have systems in place to ensure that data can be recovered in case of accidental deletion or corruption. The DevOps team has received a requirement for implementing such measures for one of the S3 buckets they are managing.

The S3 bucket name is `xfusion-s3-12781047`, enable `versioning` for this bucket using `Terraform`.

The Terraform working directory is `/home/bob/terraform`. Update the `main.tf` file (do not create a different `.tf` file) to accomplish this task.

`Note:` Right-click under the `EXPLORER` section in `VS Code` and select `Open in Integrated Terminal` to launch the terminal.

---

You need to enable S3 Bucket Versioning for bucket `xfusion-s3-12781047` using Terraform. Update only `/home/bob/terraform/main.tf`.

### Step 1: Open the Terraform directory

In VS Code Explorer:

1. Right-click the `terraform` folder.
2. Select Open in Integrated Terminal.
3. Execute:

```
cd /home/bob/terraform
ls
```

### Step 2: Update `main.tf`

Open `main.tf` and add the following Terraform resource:

```
resource "aws_s3_bucket_versioning" "versioning" {
  bucket = "xfusion-s3-12781047"

  versioning_configuration {
    status = "Enabled"
  }
}
```

Explanation:

- `aws_s3_bucket_versioning`: Terraform resource used to manage S3 bucket versioning.
- `bucket`: Specifies the existing S3 bucket name.
- `versioning_configuration`: Configures the bucket's versioning behavior.
- `status = "Enabled"`: Enables versioning so S3 maintains multiple versions of objects.

### Step 3: Initialize Terraform

```
terraform init
```

### Step 4: Validate configuration

```
terraform validate
```

Expected output:

```
Success! The configuration is valid.
```

### Step 5: Preview changes

```
terraform plan
```

Check that Terraform plans to create the S3 bucket versioning resource.

### Step 6: Apply changes

```
terraform apply
```

Type:

```
yes
```

### Step 7: Verify versioning

Run:

```
aws s3api get-bucket-versioning \
  --bucket xfusion-s3-12781047
```

Expected output:

```
{
    "Status": "Enabled"
}
```



Task complete

&#x20;S3 bucket versioning is enabled.



Important interview point: S3 Versioning protects against accidental object deletion and overwrites by retaining previous object versions. It does not replace backups or replication.
