package org.example.classementService.service;

import org.example.matchService.model.Match;
import org.example.teamService.model.Team;
import org.example.teamService.service.TeamService;

import java.util.List;

public class ClassementService {

    private final TeamService teamService;

    public ClassementService(TeamService teamService) {
        this.teamService = teamService;
    }

    public void updateClassement(Match match) {
        // Récupérer les équipes impliquées
        Team team1 = teamService.getTeamByName(match.getTeam1()).orElse(null);
        Team team2 = teamService.getTeamByName(match.getTeam2()).orElse(null);

        if (team1 == null || team2 == null) {
            System.out.println("Une ou les deux équipes du match sont introuvables. Classement non mis à jour.");
            return;
        }

        // Calcul des points
        if (match.getTeam1Score() > match.getTeam2Score()) {
            team1.setScore(team1.getScore() + 3);  // Victoire team1
        } else if (match.getTeam2Score() > match.getTeam1Score()) {
            team2.setScore(team2.getScore() + 3);  // Victoire team2
        } else {
            team1.setScore(team1.getScore() + 1);  // Match nul
            team2.setScore(team2.getScore() + 1);
        }
    }

    public void afficherClassement() {
        System.out.println("=== Classement Actuel ===");
        List<Team> teams = teamService.getAllTeams();
        teams.stream()
                .sorted((t1, t2) -> Integer.compare(t2.getScore(), t1.getScore())) // Trier par score décroissant
                .forEach(team -> System.out.printf("Nom: %s, Score: %d%n", team.getName(), team.getScore()));
    }
}
