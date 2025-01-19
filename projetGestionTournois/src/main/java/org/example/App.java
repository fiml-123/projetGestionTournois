package org.example;

import org.example.classementService.service.ClassementService;
import org.example.matchService.event.EventBus;
import org.example.matchService.event.MatchEvent;
import org.example.matchService.event.MatchEventHandler;
import org.example.matchService.event.MatchEventType;
import org.example.matchService.model.Match;
import org.example.matchService.service.MatchService;
import org.example.notificationService.event.NotificationEvent;
import org.example.notificationService.event.NotificationEventBus;
import org.example.notificationService.event.NotificationEventType;
import org.example.teamService.model.Team;
import org.example.teamService.repository.TeamRepository;
import org.example.teamService.service.TeamService;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        // Initialisation des services
        TeamRepository teamRepository = new TeamRepository();
        TeamService teamService = new TeamService(teamRepository);
        ClassementService classementService = new ClassementService(teamService);
        MatchService matchService = new MatchService();

        // Inscription des gestionnaires d'événements pour les matchs
        EventBus.subscribe(MatchEventType.CREATE_MATCH, new MatchEventHandler(classementService));
        EventBus.subscribe(MatchEventType.DELETE_MATCH, new MatchEventHandler(classementService));

        // Scanner pour les entrées utilisateur
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            // Affichage du menu principal
            System.out.println("\n=== Gestionnaire de Tournoi ===");
            System.out.println("1. Gestion des équipes");
            System.out.println("2. Organiser un match");
            System.out.println("3. Afficher le classement");
            System.out.println("4. Quitter");
            System.out.print("Choix : ");
            int choix = scanner.nextInt();
            scanner.nextLine(); // Consommer le saut de ligne restant

            switch (choix) {
                case 1: // Gestion des équipes
                    manageTeams(scanner, teamService);
                    break;

                case 2: // Organiser un match
                    organizeMatch(scanner, teamService, matchService, classementService);
                    break;

                case 3: // Afficher le classement
                    classementService.afficherClassement();
                    break;

                case 4: // Quitter
                    System.out.println("Au revoir !");
                    running = false;
                    break;

                default:
                    System.out.println("Choix invalide. Essayez de nouveau.");
            }
        }

        scanner.close();
    }

    private static void manageTeams(Scanner scanner, TeamService teamService) {
        boolean managingTeams = true;

        while (managingTeams) {
            // Sous-menu Gestion des équipes
            System.out.println("\n=== Gestion des équipes ===");
            System.out.println("1. Ajouter une équipe");
            System.out.println("2. Modifier une équipe");
            System.out.println("3. Supprimer une équipe");
            System.out.println("4. Lister les équipes");
            System.out.println("5. Retour au menu principal");
            System.out.print("Choix : ");
            int teamChoice = scanner.nextInt();
            scanner.nextLine(); // Consommer le saut de ligne restant

            switch (teamChoice) {
                case 1: // Ajouter une équipe
                    System.out.print("Nom de l'équipe : ");
                    String name = scanner.nextLine();
                    System.out.print("Nombre de joueurs : ");
                    int playerCount = scanner.nextInt();
                    scanner.nextLine();

                    try {
                        Team newTeam = teamService.createTeam(name, playerCount);
                        System.out.println("Équipe créée : " + newTeam.getName());

                        // Publier l'événement de notification
                        NotificationEventBus.connectToNotificationServer("localhost", 12345);
                        NotificationEventBus.publish(new NotificationEvent(
                                NotificationEventType.NOUVELLE_EQUIPE,
                                "Nouvelle équipe créée : " + newTeam.getName()
                        ));

                    } catch (IllegalArgumentException e) {
                        System.out.println("Erreur : " + e.getMessage());
                    }
                    break;

                case 2: // Modifier une équipe
                    System.out.print("ID de l'équipe à modifier : ");
                    long teamId = scanner.nextLong();
                    scanner.nextLine();
                    System.out.print("Nouveau nom de l'équipe : ");
                    String newName = scanner.nextLine();
                    System.out.print("Nouveau nombre de joueurs : ");
                    int newPlayerCount = scanner.nextInt();
                    scanner.nextLine();

                    boolean updated = teamService.updateTeam(teamId, newName, newPlayerCount).isPresent();
                    if (updated) {
                        System.out.println("Équipe mise à jour avec succès !");
                    } else {
                        System.out.println("Équipe introuvable avec l'ID : " + teamId);
                    }
                    break;

                case 3: // Supprimer une équipe
                    System.out.print("ID de l'équipe à supprimer : ");
                    long idToDelete = scanner.nextLong();
                    scanner.nextLine();

                    boolean deleted = teamService.deleteTeam(idToDelete);
                    if (deleted) {
                        System.out.println("Équipe supprimée avec succès !");
                    } else {
                        System.out.println("Équipe introuvable avec l'ID : " + idToDelete);
                    }
                    break;

                case 4: // Lister les équipes
                    System.out.println("\n=== Liste des équipes ===");
                    teamService.getAllTeams().forEach(team -> {
                        System.out.println("ID: " + team.getId() +
                                ", Nom: " + team.getName() +
                                ", Joueurs: " + team.getPlayerCount() +
                                ", Score: " + team.getScore());
                    });
                    break;

                case 5: // Retour au menu principal
                    managingTeams = false;
                    break;

                default:
                    System.out.println("Choix invalide. Essayez de nouveau.");
            }
        }
    }

    private static void organizeMatch(Scanner scanner, TeamService teamService, MatchService matchService, ClassementService classementService) {
        System.out.println("\n=== Organiser un match ===");
        System.out.println("1. Choisir des équipes existantes");
        System.out.println("2. Entrer les équipes manuellement");
        System.out.print("Choix : ");
        int matchChoice = scanner.nextInt();
        scanner.nextLine();

        String team1, team2;
        int score1, score2;

        if (matchChoice == 1) {
            // Lister les équipes disponibles
            System.out.println("\n=== Liste des équipes disponibles ===");
            teamService.getAllTeams().forEach(team -> {
                System.out.println("ID: " + team.getId() +
                        ", Nom: " + team.getName() +
                        ", Joueurs: " + team.getPlayerCount() +
                        ", Score: " + team.getScore());
            });

            // Choisir les équipes par ID
            System.out.print("ID de la première équipe : ");
            long id1 = scanner.nextLong();
            scanner.nextLine();
            System.out.print("ID de la deuxième équipe : ");
            long id2 = scanner.nextLong();
            scanner.nextLine();

            // Récupérer les équipes
            team1 = teamService.getTeamById(id1).map(Team::getName).orElse("Équipe introuvable");
            team2 = teamService.getTeamById(id2).map(Team::getName).orElse("Équipe introuvable");

            if (team1.equals("Équipe introuvable") || team2.equals("Équipe introuvable")) {
                System.out.println("Une ou les deux équipes sélectionnées n'existent pas. Annulation du match.");
                return;
            }

        } else {
            // Entrer les équipes manuellement
            System.out.print("Nom de la première équipe : ");
            team1 = scanner.nextLine();
            System.out.print("Nom de la deuxième équipe : ");
            team2 = scanner.nextLine();
        }

        // Entrer les scores
        System.out.print("Score de " + team1 + " : ");
        score1 = scanner.nextInt();
        System.out.print("Score de " + team2 + " : ");
        score2 = scanner.nextInt();
        scanner.nextLine();

        // Créer le match
        Match match = new Match(team1, team2, score1, score2);
        System.out.println("Match organisé : " + match);

        // Mettre à jour le classement
        classementService.updateClassement(match);

        System.out.println("Classement mis à jour !");

        NotificationEventBus.connectToNotificationServer("localhost", 12345);
        NotificationEventBus.publish(new NotificationEvent(
                NotificationEventType.MATCH_TERMINE,
                "Match terminé : " + match.getTeam1() + " (" + score1 + ") - " +
                        match.getTeam2() + " (" + score2 + ")"
        ));

    }
}
