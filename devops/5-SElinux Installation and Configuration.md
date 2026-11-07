Following a security audit, the xFusionCorp Industries security team has opted to enhance application and server security with SELinux. To initiate testing, the following requirements have been established for App server 3 in the Stratos Datacenter:



Install the required SELinux packages.

Permanently disable SELinux for the time being; it will be re-enabled after necessary configuration changes.

No need to reboot the server, as a scheduled maintenance reboot is already planned for tonight.

Disregard the current status of SELinux via the command line; the final status after the reboot should be disabled.


To permanently disable SELinux on **App Server 3** (`stapp03`) while ensuring the required packages are installed, follow these steps.

### Step-by-Step Procedure

1. **Access App Server 3:** Via SSH.
Log in to App Server 3 (`stapp03`) using your administrative credentials:

```bash
ssh banner@stapp03.stratos.xfusioncorp.com

```

*(Substitute `banner` with your assigned administrative user if applicable).*

*Verification:* Confirm you are logged into the terminal shell of `stapp03`.


2. **Install Required SELinux Packages:** Package Installation.
Install the SELinux management and utility packages (`selinux-policy`, `selinux-policy-targeted`, `policycoreutils`, and `container-selinux` if needed):

```bash
sudo yum install -y selinux-policy selinux-policy-targeted policycoreutils

```

*Verification:* Run `rpm -qa | grep selinux` to confirm the packages are installed successfully on the system.


3. **Permanently Disable SELinux Configuration:** /etc/selinux/config.
Open the SELinux configuration file in a text editor:

```bash
sudo vi /etc/selinux/config

```

Locate the line starting with `SELINUX=` and update it to:

```text
SELINUX=disabled

```

Save and close the file.

*Verification:* Run `grep ^SELINUX= /etc/selinux/config` to ensure the output reads `SELINUX=disabled`.


No reboot or immediate runtime status changes are required now, as SELinux will be completely disabled automatically following the scheduled maintenance reboot tonight.
