DropZone

DropZone est une application de bureau développée en Java qui permet d'organiser automatiquement les fichiers d'un dossier selon des règles personnalisables.

L'objectif est simple : éviter que les dossiers de téléchargement, de travail ou de bureau deviennent désordonnés en déplaçant automatiquement les fichiers vers les bons emplacements.

Fonctionnalités actuelles

1 - Création de règles de tri personnalisées

2 - Déplacement automatique des fichiers selon leur extension

3 - Gestion de plusieurs catégories

4 - Sauvegarde de la configuration

5 - Interface graphique moderne avec JavaFX

6 - Journalisation (logs) des actions effectuées

7 - Vérification automatique des mises à jour

8 - Téléchargement et installation des nouvelles versions directement depuis l'application

Exemple

Vous pouvez créer une règle telle que :

Extension	Destination
.png	Images
.jpg	Images
.mp4	Vidéos
.pdf	Documents
.zip	Archives

Lorsque DropZone analyse le dossier sélectionné, les fichiers sont automatiquement déplacés vers les dossiers correspondants.

Technologies utilisées
Java
JavaFX
Maven
Gson
Ikonli
Installation
Prérequis
Java 21 ou supérieur
Lancer depuis le JAR
java -jar DropZone.jar
État du projet

DropZone est actuellement en développement actif.

Certaines fonctionnalités prévues ne sont pas encore disponibles, notamment :

Surveillance continue de dossiers (Watchlist)
Détection automatique des nouveaux fichiers en temps réel
Règles plus avancées
Statistiques d'utilisation

Ces fonctionnalités arriveront progressivement dans les futures mises à jour.

Mise à jour

L'application dispose d'un système de mise à jour intégré permettant de télécharger automatiquement les nouvelles versions lorsqu'elles sont disponibles.

Contribution

Les suggestions, rapports de bugs et retours d'expérience sont les bienvenus.

Licence

Ce projet est actuellement distribué sans licence spécifique.
