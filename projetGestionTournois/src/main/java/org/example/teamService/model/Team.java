package org.example.teamService.model;

public class Team {
    private long id;
    private String name;
    private int score;
    private int playerCount; // Nouveau champ pour le nombre de joueurs

    public Team() {}

    public Team(long id, String name, int score, int playerCount) {
        this.id = id;
        this.name = name;
        this.score = score;
        this.playerCount = playerCount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
    }
}
