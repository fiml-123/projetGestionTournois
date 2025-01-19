package org.example.teamService.repository;

import org.example.teamService.model.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TeamRepository {
    private final List<Team> teams = new ArrayList<>();
    private long nextId = 1;

    public List<Team> findAll() {
        return teams;
    }

    public Optional<Team> findById(long id) {
        return teams.stream().filter(team -> team.getId() == id).findFirst();
    }

    public Team save(Team team) {
        if (team.getId() == 0) {
            team.setId(nextId++);
        }
        teams.add(team);
        return team;
    }

    public boolean deleteById(long id) {
        return teams.removeIf(team -> team.getId() == id);
    }
}
