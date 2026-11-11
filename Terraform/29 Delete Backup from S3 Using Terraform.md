# 29 Delete Backup from S3 Using Terraform

The Nautilus DevOps team is currently engaged in a cleanup process, focusing on removing unnecessary data and services from their AWS account. As part of the migration process, several resources were created for one-time use only, necessitating a cleanup effort to optimize their AWS environment.

A S3 bucket named datacenter-bck-636192346 already exists.

1) Copy the contents of datacenter-bck-636192346 S3 bucket to /opt/s3-backup/ directory on terraform-client host (the landing host once you load this lab).

2) Delete the S3 bucket datacenter-bck-636192346.

3) Use the AWS CLI through Terraform to accomplish this task—for example, by running AWS CLI commands within Terraform. The Terraform working directory is /home/bob/terraform. Update the main.tf file (do not create a separate .tf file) to accomplish this task.

Note: Right-click under the EXPLORER section in VS Code and select Open in Integrated Terminal to launch the terminal.

---

For this KodeKloud task, we need to perform two operations in the correct order:

1. Copy all S3 bucket contents to `/opt/s3-backup/` on the `terraform-client` host.
2. Delete the S3 bucket after the copy succeeds.

We will use Terraform's `null_resource` with `local-exec` provisioner to execute AWS CLI commands.

## Step 1: Open Terraform directory

Open VS Code Integrated Terminal and execute:

```
cd /home/bob/terraform
```

## Step 2: Update `main.tf`

Add the following configuration to `main.tf`:

```

resource "null_resource" "s3_backup_delete" {

  provisioner "local-exec" {
    command = <<-EOT
      mkdir -p /opt/s3-backup
      aws s3 sync s3://datacenter-bck-636192346 /opt/s3-backup/
      aws s3 rb s3://datacenter-bck-636192346 --force
    EOT
  }
}

```

## Step 3: Understand each command

1\. Create backup directory

```
mkdir -p /opt/s3-backup
```

Creates the destination directory if it doesn't already exist.

2\. Copy S3 bucket contents

```
aws s3 sync s3://datacenter-bck-636192346 /opt/s3-backup/
```

- Copies all objects from the S3 bucket.
- Preserves the folder structure.
- Downloads the files to the local machine.
- Does not delete existing local files.

3\. Delete S3 bucket

```
aws s3 rb s3://datacenter-bck-636192346 --force
```

- `rb` means remove bucket.
- `--force` removes objects before deleting the bucket.

## Step 4: Initialize Terraform

```
terraform init
```

This installs the required Null provider.

## Step 5: Validate configuration

```
terraform validate
```

Expected:

```
Success! The configuration is valid.
```

## Step 6: Execute Terraform

```
terraform plan
terraform apply -auto-approve
```

Terraform executes the AWS CLI commands through the `local-exec` provisioner.

## Step 7: Verify backup

Check the downloaded files:

```
ls -la /opt/s3-backup/
```

Verify that the S3 bucket has been deleted:

```
aws s3 ls
```

The bucket `datacenter-bck-636192346` should no longer appear.

## Architecture: How this works

Terraform apply

/home/bob/terraform/main.tf

local-exec provisioner

terraform-client host

Execute AWS CLI commands

Step 1: Copy

S3 bucket

/opt/s3-backup/

Step 2: Delete

S3 bucket

Bucket deleted

Important interview point: Terraform's `local-exec` provisioner runs commands on the machine where Terraform is executed, not on an AWS EC2 instance. Here, that machine is `terraform-client`.

One additional caution: The commands above assume a non-versioned bucket. If the bucket has object versioning enabled, all object versions and delete markers must also be removed before the bucket can be deleted.
