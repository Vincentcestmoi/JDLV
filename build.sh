#!/usr/bin/env bash

set -e

APP_NAME="pfc"
OUTPUT_DIR="build/jpackage"

java --version || { echo "Java n'est pas installé."; exit 1; }
./gradlew clean
./gradlew installDist
./gradlew jpackageDeb || { echo "Erreur lors du packaging Debian."; exit 1; }
if command -v wine >/dev/null 2>&1; then
    ./gradlew jpackageWin || printf "Impossible de générer le .msi sur cette machine.\n\n"
else
    printf "Wine non détecté : génération du .msi ignorée.\n\n"
fi

echo "📁 Fichiers générés dans : $OUTPUT_DIR"
ls -lh "$OUTPUT_DIR"
echo ""
echo "🐧 Pour installer le .deb :"
echo "sudo dpkg -i $OUTPUT_DIR/${APP_NAME}_*.deb"
