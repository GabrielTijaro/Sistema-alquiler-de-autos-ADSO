#!/usr/bin/env bash

DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" >/dev/null 2>&1 && pwd)"
FILE_PATH="$DIR/index.html"

if command -v xdg-open > /dev/null; then
    xdg-open "$FILE_PATH"
elif command -v sensible-browser > /dev/null; then
    sensible-browser "$FILE_PATH"
else
    python3 -m webbrowser "$FILE_PATH"
fi
