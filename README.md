# Projet Internet Movie DataBase

## Objectifs

Ce projet vise à :

1. **Réaliser un document de conception** : Fournir un diagramme de classes (UML) ou un modèle conceptuel de données (Merise), selon le choix du développeur.
2. **Mettre en place une base de données** : Utiliser JPA pour intégrer les données provenant de 6 fichiers CSV.
3. **Développer une application d'insertion de données** : Permettre l'importation des données dans la base de données.
4. **Créer une application d'extraction de données** : Offrir un menu interactif pour rechercher des informations spécifiques sur les films, les acteurs, etc.

## Description

Le projet s'appuie sur 6 fichiers CSV contenant des informations sur les films, les acteurs, les réalisateurs, les rôles, les collaborations entre réalisateurs et films, ainsi que les pays.

### Tâches

#### Tâche n°1 : Analyse des fichiers CSV

Analyser les structures des fichiers pour comprendre leurs contenances et les relations entre les données.

#### Tâche n°2 : Document de conception

Créer un document de conception contenant les diagrammes nécessaires (UML ou Merise) et le placer dans un dossier `conception` à la racine du projet.

#### Tâche n°3 : Application d'insertion de données

Développer une application capable de lire les fichiers CSV et d'insérer les données dans une base de données en utilisant JPA.

#### Tâche n°4 : Application d'extraction de données

Créer une application interactive offrant un menu pour effectuer différentes recherches dans la base de données, telles que :

1. Afficher la filmographie d'un acteur.
2. Afficher le casting d'un film.
3. Afficher les films sortis entre deux années données.
4. Afficher les films communs à deux acteurs.
5. Afficher les acteurs communs à deux films.
6. Afficher les films sortis entre deux années données et ayant un acteur donné au casting.
7. Trouver le plus court chemin entre deux acteurs (difficile).

### Exigences

#### Exigence n°1 : Qualité du code

- Rédiger la documentation Javadoc.
- Structurer le code de manière logique et propre.

#### Exigence n°2 : Unicité des données

- Les lieux de naissance, les pays, les langues, les genres, et les dates doivent être représentés comme des objets distincts plutôt que comme des chaînes de caractères.
- Assurer l'unicité des lieux de naissance, des pays, des langues, et des genres dans la base de données.
- Utiliser `LocalDate` ou `LocalDateTime` pour représenter les dates en Java.

## Contribution

Pour contribuer au projet, veuillez suivre les instructions de dépôt et de développement disponibles dans le README principal.