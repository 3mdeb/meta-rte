# RTE features setup

## Network

### Setup MAC adress at U-Boot

After flashing the card with the new image the `MAC` is not changing.
This situation may happen because the `MAC` address is stored in `U-Boot`
environment variable. You can change the address by changing the variable from
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
    => reset
    ```
