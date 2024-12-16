package com.football.scoreboard;

public class Match {
    private final String homeTeam;
    private final String awayTeam;
    private int homeScore;
    private int awayScore;
    private final long startTime;

    public Match(String homeTeam, String awayTeam, long startTime) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeScore = 0;
        this.awayScore = 0;
        this.startTime = startTime;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public int getHomeScore() {
        return homeScore;
    }

    public int getAwayScore() {
        return awayScore;
    }

    /**
     * Update the teams score.
     *
     * @param homeScore the name of the home team
     * @param awayScore the name of the away team
     * @throws IllegalArgumentException if any of the team scores is negative
     */
    public void updateScore(int homeScore, int awayScore) {
        ValidatorUtils.validatePositiveInteger(homeScore, awayScore, "Scores cannot be negative.");

        this.homeScore = homeScore;
        this.awayScore = awayScore;
    }

    public int getTotalScore() {
        return homeScore + awayScore;
    }

    public long getStartTime() {
        return startTime;
    }

    @Override
    public String toString() {
        return homeTeam + " " + homeScore + " - " + awayTeam + " " + awayScore;
    }
}