package org.example.teamService.service;

import org.example.teamService.model.Team;
import org.example.teamService.repository.TeamRepository;

import java.util.List;
import java.util.Optional;

public class TeamService {
    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public Optional<Team> getTeamByName(String name) {
        return teamRepository.findAll().stream()
                .filter(team -> team.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    // Créer une équipe
    public Team createTeam(String name, int playerCount) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Le nom de l'équipe ne peut pas être vide.");
        }
        if (playerCount <= 0) {
            throw new IllegalArgumentException("Une équipe doit avoir au moins un joueur.");
        }
        return teamRepository.save(new Team(0, name, 0, playerCount));
    }

    // Modifier une équipe existante
    public Optional<Team> updateTeam(long id, String name, int playerCount) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Le nom de l'équipe ne peut pas être vide.");
        }
        if (playerCount <= 0) {
            throw new IllegalArgumentException("Une équipe doit avoir au moins un joueur.");
        }

        return teamRepository.findById(id).map(existingTeam -> {
            existingTeam.setName(name);
            existingTeam.setPlayerCount(playerCount);
            return existingTeam;
        });
    }

    // Supprimer une équipe
    public boolean deleteTeam(long id) {
        return teamRepository.deleteById(id);
    }

    // Récupérer toutes les équipes
    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    // Trouver une équipe par ID
    public Optional<Team> getTeamById(long id) {
        return teamRepository.findById(id);
    }
}
