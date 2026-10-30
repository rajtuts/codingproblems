# Create Key Pair Using Terraform

Create the `main.tf` file inside `/home/bob/terraform` with the following Terraform configuration. This uses the AWS provider to create an RSA key pair named `nautilus-kp` and save the private key to `/home/bob/nautilus-kp.pem`.

## 1. Create `main.tf`

Open the VS Code terminal and run:

```
cd /home/bob/terraform
nano main.tf
```

Add this code to `main.tf`:

```

resource "tls_private_key" "nautilus_key" {
  algorithm = "RSA"
  rsa_bits  = 2048
}

resource "aws_key_pair" "nautilus_kp" {
  key_name   = "nautilus-kp"
  public_key = tls_private_key.nautilus_key.public_key_openssh
}

resource "local_file" "private_key" {
  content         = tls_private_key.nautilus_key.private_key_pem
  filename        = "/home/bob/nautilus-kp.pem"
  file_permission = "0400"
}

```

Save and exit Nano: press `Ctrl+O`, `Enter`, then `Ctrl+X`.

## 2. Initialize and apply Terraform

Run these commands in the terminal:

```
terraform init
terraform plan
terraform apply
```

Type `yes` when prompted by `terraform apply`.

Note: Your AWS credentials must be configured, and the AWS, TLS, and local providers must be available. Terraform will install the required providers automatically when you initialize.

## 3. Verify the result

Check that the private key file exists:

```
ls -l /home/bob/nautilus-kp.pem
```

Verify the key pair in AWS:

```
aws ec2 describe-key-pairs \
  --key-names nautilus-kp
```

Expected results:

- Key pair name: `nautilus-kp`
- Key type: `rsa`
- Private key file: `/home/bob/nautilus-kp.pem`
- File permission: `400` (owner read-only)

Important: Keep the private key secure. Terraform also stores the private key in its state file, so protect `terraform.tfstate` and never commit it to source control.
