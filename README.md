# README - Application de Gestion de Tournoi

## Introduction
L'application de gestion de tournoi est un outil interactif permettant :
- La gestion des équipes (ajout, modification, suppression, affichage).
- L'organisation des matchs entre équipes.
- Le suivi et la mise à jour du classement en temps réel.
- La notification des événements importants.

Cette application est structurée selon une architecture modulaire favorisant la maintenance, l'extensibilité et la performance.

---

## Prérequis
- **JDK 11 ou version supérieure**
- **Maven** (optionnel pour la gestion des dépendances)

### Dépendances nécessaires
Les bibliothèques ou outils externes suivants peuvent être ajoutés au projet :
- **Lombok** : Réduction du code boilerplate (annotations pour getters, setters, etc.).
- **JUnit 5** : Tests unitaires.

---

## Installation
1. Clonez le dépôt :
   ```bash
   git clone https://github.com/fiml-123/gestion-tournoi.git
   ```

2. Accédez au répertoire du projet :
   ```bash
   cd gestion-tournoi
   ```

3. Compilez le projet avec Maven (si applicable) :
   ```bash
   mvn clean install
   ```

4. Lancez l'application :
   ```bash
   java -jar target/gestion-tournoi.jar
   ```

---

## Fonctionnalités Principales

### Menu Principal
- **Gestion des équipes** :
  - Ajouter, modifier, supprimer ou afficher les équipes.
- **Organiser un match** :
  - Sélectionnez les équipes et enregistrez leurs scores.
- **Afficher le classement** :
  - Affiche les équipes classées par score.
- **Quitter** :
  - Ferme l'application.

### Notifications
Les événements importants (comme la fin d'un match ou la création d'une équipe) déclenchent des notifications visibles dans l'application.

---

## Contact
Pour toute question ou suggestion, contactez :
- GitHub : [fiml-123](https://github.com/fiml-123/)

---

# README - Qui à fait quoi ?

## Ensemble
Ce qui à été fait pendant les heures de cours :
- L'architecture globale de l'application (packages, structure des classes).
- Première version de l'application.

## Van
- Correction des erreurs du code.
- Amélioration des fonctionnalitées.
- Test des fonctionnalités.

## Farah
- Rédaction de la documentation.
- Création des diagrammes.

---

# README - La place de L'IA ?

Nous avons eu reccours à ChatGPT lors de la création de l'application notament à ces étapes :

- **Création de l'archithécture de base** :
  - Cela nous à aider à mieux commprendre l'énoncé du projet et donc à avoir une meilleur vision de l'architecture attendue.
- **Organisation du code** :
  - Mieux organiser nos classes et leur interactions.
- **Correction** :
  - Corriger quelques bugs mais pas tous, la plus part du temps corrigé par l'humain.


