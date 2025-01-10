package com.football.scoreboard;

import com.football.scoreboard.exceptions.MatchAlreadyExistsException;
import com.football.scoreboard.exceptions.MatchNotFoundException;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import static com.football.scoreboard.ValidatorUtils.validateInProgressMatch;
import static com.football.scoreboard.ValidatorUtils.validateNonNullOrEmpty;

public class ScoreBoard {
    private final List<Match> matches;

    public ScoreBoard() {
        this.matches = new CopyOnWriteArrayList<>();
    }

    /**
     * Starts a new match between two teams.
     *
     * @param homeTeam the name of the home team
     * @param awayTeam the name of the away team
     * @throws IllegalArgumentException    if team's name are empty/null
     * @throws MatchAlreadyExistsException if a match between these teams is already in progress
     * @long System.nanoTime() offers higher precision, no additional state is needed and it's inherently thread-safe
     */
    public void startMatch(String homeTeam, String awayTeam) {
        validateNonNullOrEmpty(homeTeam, awayTeam, "Teams' name cannot be empty.");
        validateInProgressMatch(matches, homeTeam, awayTeam, "This Match is already in progress.");

        long startTime = System.nanoTime();
        Match match = new Match(homeTeam, awayTeam, startTime);
        matches.add(match);
    }

    public void updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore) {
        Match match = findMatch(homeTeam, awayTeam);
        match.updateScore(homeScore, awayScore);
    }

    public void finishMatch(String homeTeam, String awayTeam) {
        Match match = findMatch(homeTeam, awayTeam);
        matches.remove(match);
    }

    public List<Match> getScoreBoards() {
        return matches.stream()
                .sorted(Comparator
                        .comparingInt(Match::getTotalScore).reversed()
                        .thenComparing(Comparator.comparingLong(Match::getStartTime).reversed()))
                .collect(Collectors.toList());
    }

    /**
     * Find the match in order to update or finish it.
     *
     * @param homeTeam the name of the home team
     * @param awayTeam the name of the away team
     * @throws MatchNotFoundException if no match is found
     */
    private Match findMatch(String homeTeam, String awayTeam) {
        return matches.stream()
                .filter(m -> m.getHomeTeam().equals(homeTeam) && m.getAwayTeam().equals(awayTeam))
                .findFirst()
                .orElseThrow(() -> new MatchNotFoundException("Match not found."));
    }

    private Match findOneMatch(String homeTeam) {
        return matches.stream()
                .filter(m -> m.getHomeTeam().equals(homeTeam))
                .findFirst()
                .orElseThrow(() -> new MatchNotFoundException("Match not found."));
    }

    public int getScore(String homeTeam) {
        validateNonNullOrEmpty(homeTeam, "Team name cannot be empty.");
        return findOneMatch(homeTeam).getHomeScore();
    }
}