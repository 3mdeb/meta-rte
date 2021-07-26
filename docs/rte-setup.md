# RTE features setup

## Network

### Setup MAC adress at U-Boot

After flashing the card with the new image, the `MAC` will be generated from the
board's serial id and stored in `U-Boot`'s environment variable on the first
boot.

You can change the address for a single boot by modifying the variable from
`U-Boot` command line. Execute the following instructions:
1. Stop loading the `U-Boot` by hitting any key:
    ```
    Hit any key to stop autoboot:  0
    ```
2. Overwrite the variable `ethaddres`:
    ```
    => setenv ethaddr 9a:08:5d:c7:cb:ce
    ```
3. Save changes:
    ```
    => saveenv
    ```
4. Restart the platform:
    ```
    => run bootcmd
    ```

After reboot, the MAC address will return to the original one.
