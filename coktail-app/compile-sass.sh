#!/bin/bash

# Script pour compiler le SCSS en CSS
echo "Compilation du SCSS en CSS..."

# Compiler le SCSS en CSS
sass src/main/resources/static/style.scss src/main/resources/static/css/style.css

echo "Compilation terminée !" 