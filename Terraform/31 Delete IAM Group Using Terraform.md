31 Delete IAM Group Using Terraform

The Nautilus DevOps team is currently engaged in a cleanup process, focusing on removing unnecessary data and services from their AWS account. As part of the migration process, several resources were created for one-time use only, necessitating a cleanup effort to optimize their AWS environment.

Delete an IAM group named `iamgroup_james` using `terraform`. Make sure to keep the provisioning code, as we might need to provision this instance again later.

The Terraform working directory is `/home/bob/terraform`.

`Note:` Right-click under the `EXPLORER` section in `VS Code` and select `Open in Integrated Terminal` to launch the terminal.

---

For this KodeKloud task, you need to delete the existing IAM group `iamgroup_james` while preserving its Terraform configuration so it can be recreated later.

The correct approach is to use Terraform's targeted destroy command.

## Step 1: Open Terraform directory

Open VS Code Integrated Terminal and execute:

```
cd /home/bob/terraform
```

## Step 2: Initialize Terraform

```
terraform init
```

## Step 3: Identify the IAM group resource

Run:

```
terraform state list
```

Look for a resource similar to:

```
aws_iam_group.iamgroup_james
```

If you are unsure of the resource address, inspect `main.tf`:

```
cat main.tf
```

For example, the provisioning code might be:

```
resource "aws_iam_group" "iamgroup_james" {
  name = "iamgroup_james"
}
```

Keep this code unchanged.

## Step 4: Delete only the IAM group

Assuming the resource address is `aws_iam_group.iamgroup_james`, execute:

```
terraform destroy -target=aws_iam_group.iamgroup_james
```

When prompted, enter:

```
yes
```

## Step 5: Verify deletion

Run:

```
aws iam get-group --group-name iamgroup_james
```

Expected: `NoSuchEntity` error, confirming the group no longer exists.

Alternatively:

```
aws iam list-groups --query "Groups[?GroupName=='iamgroup_james']"
```

Expected:

```
[]
```

## Important points

| Operation                            | Result                                             |
| ------------------------------------ | -------------------------------------------------- |
| Remove resource block from `main.tf` | Provisioning code lost                             |
| `terraform destroy -target=...`      | Deletes IAM group and removes it from state        |
| `terraform state rm`                 | Removes Terraform tracking only; IAM group remains |



Correct approach

&#x20;Use targeted Terraform destroy.



Remember: Keep the original `aws_iam_group` resource block in `main.tf`. Do not run `terraform apply` afterward, because Terraform may recreate the group.

If Terraform reports that the group is not empty, first check for attached users and remove their group memberships before retrying.
