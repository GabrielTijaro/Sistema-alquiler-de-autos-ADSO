#!/usr/bin/env bash
# =====================================================================
# Lanzador de la Aplicación Web Operativa (Fase 6: Programa Funcional)
# Neiva Rent a Car - SENA ADSO Ficha 3239188 - Grupo 06
# =====================================================================

DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" >/dev/null 2>&1 && pwd)"
FILE_PATH="$DIR/index.html"

echo "Abriendo Neiva Rent a Car (Programa Funcional) en su navegador..."
if command -v xdg-open > /dev/null; then
    xdg-open "$FILE_PATH"
elif command -v sensible-browser > /dev/null; then
    sensible-browser "$FILE_PATH"
else
    python3 -m webbrowser "$FILE_PATH"
fi
