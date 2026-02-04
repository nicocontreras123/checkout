#!/bin/bash
# Script para ejecutar con PostgreSQL (Railway)

if [ -f .env ]; then
    export $(cat .env | xargs)
    echo "Iniciando con PostgreSQL..."
    mvn spring-boot:run
else
    echo "Error: Crear archivo .env basado en .env.example"
    exit 1
fi
