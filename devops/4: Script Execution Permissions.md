# 4: Script Execution Permissions
To grant executable permissions to `/tmp/xfusioncorp.sh` on App Server 1 (`stapp01`) so that all users can run it, you need to connect to the server and update its file permissions using `chmod`.

### Step-by-Step Procedure

1. **Access App Server 1:** Via SSH.
Log in to App Server 1 (`stapp01`) using your administrative credentials:

```bash
ssh tony@stapp01.stratos.xfusioncorp.com

```

*(Substitute `tony` with the appropriate user assigned to your environment).*

*Verification:* Ensure you have successfully accessed the command-line interface of `stapp01`.


2. **Grant Executable Permissions to All Users:** File Permissions.
Run the `chmod` command with `sudo` to add execute permissions for user, group, and others (`a+x` or `+x`):

```bash
sudo chmod +x /tmp/xfusioncorp.sh

```

*Verification:* Run `ls -l /tmp/xfusioncorp.sh` and ensure the permission string includes `x` for all user classes (e.g., `-rwxr-xr-x` or similar):

```bash
ls -l /tmp/xfusioncorp.sh

```


3. **Verify Script Execution:** Testing Execution.
Test running the script directly to ensure it executes without permission errors:

```bash
/tmp/xfusioncorp.sh

```

*Verification:* The script should run or initiate its process rather than throwing a "Permission denied" error.
