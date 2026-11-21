#!/usr/bin/env bash
# =====================================================================
# Sistema de Gestión de Alquiler de Vehículos - Neiva Rent a Car
# SENA Regional Huila - Ficha 3239188 - Grupo 06
# Script de Ejecución Rápida en 1 Clic (Fase 6: Programa Funcional)
# =====================================================================

DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" >/dev/null 2>&1 && pwd)"
JAR_PATH="$DIR/NeivaRentACar.jar"

if [ -f "$JAR_PATH" ]; then
    echo "Iniciando Neiva Rent a Car..."
    java -jar "$JAR_PATH"
else
    echo "[ERROR] No se encontró el archivo ejecutable NeivaRentACar.jar"
    echo "Asegúrese de haber compilado el proyecto."
fi
