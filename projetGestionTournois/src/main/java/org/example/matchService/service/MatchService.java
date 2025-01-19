package org.example.matchService.service;
import org.example.matchService.model.Match;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MatchService {
    private final List<Match> matches = new ArrayList<>();
    private long nextId = 1;

    public List<Match> getAllMatches() {
        return matches;
    }

    public Optional<Match> getMatchById(long id) {
        return matches.stream().filter(match -> match.getId() == id).findFirst();
    }

    public Match createMatch(Match match) {
        match.setId(nextId++);
        matches.add(match);
        return match;
    }

    public Optional<Match> updateMatch(long id, String team1, String team2, int team1Score, int team2Score) {
        return getMatchById(id).map(existingMatch -> {
            existingMatch.setTeam1(team1);
            existingMatch.setTeam2(team2);
            existingMatch.setTeam1Score(team1Score);
            existingMatch.setTeam2Score(team2Score);
            return existingMatch;
        });
    }

    public void deleteMatch(long id) {
        matches.removeIf(match -> match.getId() == id);
    }
}
