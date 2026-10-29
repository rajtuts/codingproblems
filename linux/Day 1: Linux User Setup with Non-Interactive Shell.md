# Day 1: Linux User Setup with Non-Interactive Shell

To create user `kirsty` with a non-interactive shell on App Server 2, follow these steps.

### 1. Connect to App Server 2

Log in to the Nautilus jump host, then connect to App Server 2 using its assigned hostname or IP address.

```
ssh <your-username>@<app-server-2>
```

### 2. Create the user with a non-interactive shell

Run:

```
sudo useradd -s /sbin/nologin kirsty
```

This creates `kirsty` with `/sbin/nologin` as the login shell, preventing interactive logins.

If the command reports that the user already exists, update the shell instead:

```
sudo usermod -s /sbin/nologin kirsty
```

### 3. Verify the configuration

Run:

```
getent passwd kirsty
```

The output should end with:

```
/sbin/nologin
```

Note: On some Linux distributions, the non-interactive shell is located at `/usr/sbin/nologin`. You can check the available path with:

```
which nologin
```

Use the valid path on App Server 2.
