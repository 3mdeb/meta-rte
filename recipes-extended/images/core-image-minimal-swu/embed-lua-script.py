#!/usr/bin/env python3
""" Why this script exists:
    https://sbabic.github.io/swupdate/sw-description.html#preinstall

> Because there is no fix order in the SWU, an artifact can be packed before
> any script in the SWU. The right way is to write an “embedded-script” in Lua
> inside sw-description
"""

import re
import argparse


def main():
    parser = argparse.ArgumentParser(description="""
        Embeds a Lua script into a SWUpdate sw-description file as an
        `embedded-script` attribute, with correct libconfig string escaping.
    """)
    parser.add_argument("sw_description")
    parser.add_argument("lua_script")
    parser.add_argument("output")
    args = parser.parse_args()

    with open(args.sw_description, "r") as f:
        sw_description = f.read()

    with open(args.lua_script, "r") as f:
        lua_code = f.read()

    result = insert_embedded_script(sw_description, lua_code)

    with open(args.output, "w") as f:
        f.write(result)

    print(f"Wrote {args.output} with embedded-script from {args.lua_script}")


def strip_lua_line_comments(lua_code: str) -> str:
    """Drop lines that are entirely a `--` line comment. Lines starting with
    `--[` are left untouched (could be a `--[[` / `--[=[` block comment opener,
    which we don't attempt to close/strip).

    Function assumes lua script doesn't contain multiline strings that start with `--`!!
    """
    lines = lua_code.splitlines()
    kept = [
        line for line in lines
        if not (line.strip().startswith("--") and not line.strip().startswith("--["))
    ]
    return "\n".join(kept)


def lua_to_libconfig_string(lua_code: str) -> str:
    """Escape Lua source so it can be embedded as a libconfig string literal."""
    # Order matters: escape backslashes first, then quotes, then newlines.
    lua_code = strip_lua_line_comments(lua_code)
    escaped = lua_code.replace("\\", "\\\\")
    escaped = escaped.replace('"', '\\"')
    escaped = escaped.replace("\r\n", "\n").replace("\n", "\\n")
    return escaped


def insert_embedded_script(sw_description: str, lua_code: str) -> str:
    escaped = lua_to_libconfig_string(lua_code)
    line = f'\tembedded-script = "{escaped}";\n'

    # Insert right after the opening brace of the top-level "software" block,
    # so embedded-script sits at global scope (sibling of "version", "rte", ...).
    pattern = re.compile(r"(software\s*=\s*\{\s*\n)")
    if not pattern.search(sw_description):
        raise ValueError('Could not find "software = {" opening block in sw-description')

    return pattern.sub(lambda m: m.group(1) + line, sw_description, count=1)


if __name__ == "__main__":
    main()
