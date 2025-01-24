```markdown
# Documentation - Application de Gestion de Tournoi

## Table des Matières
- [Introduction](#introduction)
- [Architecture du Projet](#architecture-du-projet)
- [Description des Packages](#description-des-packages)
- [Fonctionnement de l'Application](#fonctionnement-de-lapplication)
  - [Menu Principal](#menu-principal)
  - [Gestion des Équipes](#gestion-des-équipes)
  - [Organisation des Matchs](#organisation-des-matchs)
  - [Classement](#classement)
- [Notifications](#notifications)
- [Architecture Globale](#architecture-globale)
- [Avantages de l'architecture](#avantages-de-larchitecture)
- [Améliorations possibles](#améliorations-possibles)

---

## Introduction

Cette application est un gestionnaire de tournoi interactif permettant de gérer des équipes, organiser des matchs et maintenir un classement en temps réel. Elle utilise des services modulaires pour encapsuler les responsabilités et des bus d'événements pour synchroniser les actions.

---

## Architecture du Projet

### Organisation des Packages

- `org.example.classementService` : Gestion du classement des équipes.
- `org.example.matchService` : Organisation des matchs et gestion des événements liés.
- `org.example.notificationService` : Gestion des notifications, serveur client pour diffuser des messages.
- `org.example.teamService` : Gestion des équipes (CRUD).

### Flux Général
1. L'utilisateur interagit avec l'application via un menu principal.
2. Les actions génèrent des événements diffusés par le bus d'événements.
3. Les services appropriés traitent les événements pour effectuer les mises à jour ou diffuser des notifications.

---

## Description des Packages

### **Classement Service**
- **Classe : `ClassementService`**
  - Met à jour le classement des équipes en fonction des résultats des matchs.
  - Affiche le classement actuel.

### **Match Service**
- **Classe : `MatchService`**
  - Gestion des données relatives aux matchs : création, suppression, modification.
- **Bus d'Événements**
  - **Classe : `EventBus`**
    - Diffuse des événements liés aux matchs.
  - **Types d'Événements** : `MatchEventType` (`CREATE_MATCH`, `DELETE_MATCH`).

### **Notification Service**
- **Serveur de Notifications**
  - **Classe : `NotificationServer`**
    - Gère les connexions des clients et diffuse les messages.
- **Client de Notifications**
  - **Classe : `NotificationClient`**
    - Reçoit les notifications du serveur.
- **Bus d'Événements**
  - **Classe : `NotificationEventBus`**
    - Diffuse des notifications à travers l'application.
  - **Types d'Événements** : `NotificationEventType` (`MATCH_TERMINE`, `NOUVELLE_EQUIPE`).

### **Team Service**
- **Classe : `TeamService`**
  - Ajoute, modifie, supprime et liste les équipes.
- **Classe : `TeamRepository`**
  - Stocke les données des équipes.

---

## Fonctionnement de l'Application

### Menu Principal
Le menu principal propose les options suivantes :
1. **Gestion des équipes** : Accès au sous-menu pour ajouter, modifier, supprimer ou afficher les équipes.
2. **Organiser un match** : Permet d'organiser un match entre deux équipes.
3. **Afficher le classement** : Affiche le classement des équipes.
4. **Quitter** : Termine l'application.

### Gestion des Équipes
#### Fonctionnalités :
1. **Ajouter une équipe** : 
   - Saisie du nom et du nombre de joueurs.
   - Publication d'une notification via `NotificationEventBus`.
2. **Modifier une équipe** : 
   - Modification des données d'une équipe existante par son ID.
3. **Supprimer une équipe** : 
   - Suppression d'une équipe par son ID.
4. **Lister les équipes** : 
   - Affiche toutes les équipes avec leurs détails (nom, score, joueurs).

### Organisation des Matchs
#### Étapes :
1. Sélection ou saisie manuelle des noms des équipes.
2. Enregistrement des scores des deux équipes.
3. Création d'un objet `Match`.
4. Mise à jour du classement via `ClassementService`.
5. Publication d'une notification via `NotificationEventBus`.

Exemple :
```java
Match match = new Match("Équipe A", "Équipe B", 3, 2);
classementService.updateClassement(match);
NotificationEventBus.publish(new NotificationEvent(
    NotificationEventType.MATCH_TERMINE,
    "Match terminé : Équipe A (3) - Équipe B (2)"
));
```

### Classement
#### Fonctionnalités :
- Affichage du classement des équipes trié par score.
- Mise à jour automatique après chaque match.

### Notifications
Les notifications sont gérées via le `NotificationEventBus` :
- Connexion à un serveur de notifications.
- Publication d'événements liés aux équipes ou aux matchs.

Exemple de notification :
```java
NotificationEventBus.publish(new NotificationEvent(
    NotificationEventType.NOUVELLE_EQUIPE,
    "Nouvelle équipe créée : Équipe C"
));
```

---

## Architecture Globale

```text
App
 ├── TeamService
 │    ├── TeamRepository
 │    └── Team
 ├── ClassementService
 │    └── TeamService
 ├── MatchService
 │    └── Match
 └── NotificationEventBus
      ├── NotificationEvent
      └── NotificationEventType
```

---

## Avantages de l'architecture
### 1. Modularité
Chaque fonctionnalité (gestion des équipes, matchs, notifications, classement) est isolée dans un module distinct, ce qui facilite la maintenance et les tests.

### 2. Extensibilité
L'utilisation d'un bus d'événements permet d'ajouter de nouveaux types d'événements ou de gestionnaires sans modifier le code existant.

### 3. Réutilisabilité
Les composants comme `EventBus` et `NotificationEventBus` peuvent être réutilisés dans d'autres parties de l'application ou dans d'autres projets.

### 4. Asynchronie
Grâce au modèle orienté événements, l'application peut gérer plusieurs tâches de manière asynchrone, ce qui améliore les performances.

### 5. Séparation des responsabilités
Chaque classe ou service a une responsabilité unique, conformément au principe SOLID.

---

## Améliorations possibles

### 1. Tests unitaires
- Ajouter des tests unitaires pour chaque composant afin de garantir la fiabilité de l'application.

### 2. Persistance des données
- Utiliser une base de données relationnelle (comme MySQL) ou non relationnelle (comme MongoDB) pour stocker les équipes et les matchs au lieu d'utiliser des listes en mémoire.

### 3. Gestion des erreurs
- Implémenter une gestion des exceptions plus robuste, notamment pour gérer les cas où les services externes (comme le serveur de notifications) sont indisponibles.

### 4. Interface utilisateur améliorée
- Remplacer l'interface en ligne de commande par une interface graphique (JavaFX, Swing) ou une application web.

### 5. Sécurité
- Ajouter une authentification et une autorisation pour protéger l'accès à certaines fonctionnalités (par exemple, modification des équipes ou organisation des matchs).

### 6. Documentation technique
- Générer une documentation API détaillée (par exemple, avec Swagger si l'application évolue vers un service REST).

### 7. Optimisation des performances
- Utiliser des structures de données plus efficaces pour gérer les listes d'équipes et de matchs lorsque le volume de données augmente.

---
```