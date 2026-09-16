# Le jeu de la vie de de Conway

## 🛠️ 1. Prérequis

### Java

L’application utilise Java 21.

### Debian

Installer OpenJDK 21 :
```
sudo apt install openjdk-21-jdk
```

Vérifiez avec :
```
java --version
```

### Windows

Installer Temurin 21 (Adoptium).

### Gradle

Le projet utilise le Gradle Wrapper, donc aucune installation n’est nécessaire.

Vérifiez avec :
```
./gradlew --version
```

### JavaFX

JavaFX est automatiquement téléchargé par Gradle

## Sur Debian, les étapes 2 à 5 peuvent être remplacé par le lancement du shell mis à disposition

Donnez les droits d’exécution au script :
```
chmod +x build.sh
```
Lancez le script :
```
./build.sh
```
Vous devriez avoir un retour indiquant la réussite de la compilation et du packaging.

Exemple :
```
javac 21.0.2

BUILD SUCCESSFUL in 1s
1 actionable task: 1 executed

BUILD SUCCESSFUL in 2s
5 actionable tasks: 5 executed

BUILD SUCCESSFUL in 1m
6 actionable tasks: 1 executed, 5 up-to-date

> Task :jpackageWin FAILED
Error: Invalid or unsupported type: [msi]

BUILD FAILED in 1s
6 actionable tasks: 1 executed, 5 up-to-date
Impossible de générer le .msi sur cette machine.

📁 Fichiers générés dans : build/jpackage
total 42M
-rw-r--r-- 1 test test 42M  2 juil. 15:52 pfc_1.0_amd64.deb

🐧 Pour installer le .deb :
    sudo dpkg -i build/jpackage/jdlv_*.deb
```

Suivez les instructions pour lancer l’application.
Exemple :
```
sudo dpkg -i build/jpackage/jdlv_*.deb
```

## 🏗️ 2. Compilation de l’application

Dans le dossier du projet :
```
./gradlew clean
./gradlew installDist
```

Cela génère une distribution dans :
```
build/install/JDLV
```

Pour lancer l’application depuis cette distribution :
```
./build/install/JDLV/bin/JDLV
```
## 📦 3. Packaging natif avec jpackage

Le projet contient deux tâches Gradle :

    jpackageDeb → génère un .deb pour Debian

    jpackageWin → génère un .msi pour Windows

Ces tâches doivent être présentes dans le build.gradle

## 🐧 4. Générer un paquet Debian (.deb)
## 1️⃣ Construire l’application
```
./gradlew installDist
```

## 2️⃣ Générer le .deb
```
./gradlew jpackageDeb
```

Le fichier généré se trouve dans :
```
build/jpackage/
```

Exemple :
```
build/jpackage/JDLV_1.0-1_amd64.deb
```

## 3️⃣ Installer le paquet
```
sudo dpkg -i build/jpackage/JDLV_1.0-1_amd64.deb
```

## 4️⃣ Lancer l’application
```
JDLV
```
Ou via le menu des applications.

## 🪟 5. Générer un installateur Windows (.msi)
1️⃣ Construire l’application
```
gradlew installDist
```

## 2️⃣ Générer le .msi
```
gradlew jpackageWin
```

Le fichier généré se trouve dans :
```
build/jpackage/JDLV-1.0.msi
```

## 3️⃣ Installer

Double‑cliquez sur le fichier .msi.
## 4️⃣ Lancer l’application

Depuis :

    Menu Démarrer

    Raccourci sur le bureau (si activé)

    C:\Program Files\JDLV\JDLV.exe

## ▶️ 6. Utilisation de l’application
Commandes

    ENTER (menu principal) → lancer une nouvelle partie

    ENTER (menu de jeu) → reprendre la partie

    Clic droit (en jeu) → changer l'état d'une cellule
    
    Q (en jeu) → arrêter la partie et revenir au menu

    R (en jeu) → lancer une nouvelle partie

    ESPACE (en jeu) → mettre en pause/relancer le jeu

    M (en jeu) → ouvrir le menu de jeu

    + (en jeu) → ralentir la vitesse du jeu

    + (en jeu) → accélérer la vitesse du jeu

    c (en jeu) → changer la couleur d'une cellule au hasard


## 🧪 7. Dépannage
### JavaFX : erreur “wrong class version”

Cela signifie que JavaFX est compilé pour une version de Java supérieure.
Solution : utiliser Java 21 + JavaFX 21.

### Gradle : tâche introuvable

Vérifier que les tâches jpackageDeb et jpackageWin sont bien dans build.gradle.

## 8. Désinstallation de l’application

### Debian

Pour désinstaller le paquet Debian :
```
sudo dpkg -r JDLV
```

Pour supprimer les fichiers de configuration :
```
sudo dpkg -P JDLV
```

Vous pouvez vérifier que le paquet est bien désinstallé avec :
```
dpkg -l | grep JDLV
```

### Windows
Pour désinstaller l’application sur Windows, vous pouvez utiliser l’outil de désinstallation intégré à Windows :
```
Control Panel > Programs and Features > JDLV > Uninstall
```

