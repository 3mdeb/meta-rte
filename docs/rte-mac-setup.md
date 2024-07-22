# Setup MAC address at U-Boot

After flashing the card with the new image, the `MAC` will be generated from the
board's `Serial ID` and stored in `U-Boot`'s environment variable on the first
boot.

If no `Serial ID` is found in U-Boot environment, the `MAC` will be regenerated.

If `Serial ID` change (e.g. by booting the SD card on another device), the
`MAC` will be regenerated again.

If `Serial ID` is the same, only unset `MAC` addresses will be generated.

You can change the address by modifying the variable from
`U-Boot` command line. Execute the following instructions:
1. Stop loading the `U-Boot` by hitting any key:

    ```null
    Hit any key to stop autoboot:  0
    ```

2. Overwrite the variable `ethaddres`:

    ```null
    => setenv ethaddr 9a:08:5d:c7:cb:ce
    ```

3. Save changes:

    ```null
    => saveenv
    ```

4. Restart the platform:

    ```null
    => run bootcmd
    ```

> Note: If `Serial ID` change, you will lost your custom `MAC`.
