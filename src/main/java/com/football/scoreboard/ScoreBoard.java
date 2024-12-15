package com.football.scoreboard;

import com.football.scoreboard.exceptions.MatchAlreadyExistsException;
import com.football.scoreboard.exceptions.MatchNotFoundException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
/**
 * @long counter: A unique counter that is incremented with each match. This ensures that each match
 *                receives a unique start time, even if multiple matches are started at the same exact millisecond.
 */
public class ScoreBoard {
    private final List<Match> matches;
    private long counter = 0;

    public ScoreBoard() {
        this.matches = new ArrayList<>();
    }

    /**
     * Starts a new match between two teams.
     *
     * @param homeTeam the name of the home team
     * @param awayTeam the name of the away team
     * @throws MatchAlreadyExistsException if a match between these teams is already in progress
     * @long startTime generates a unique start time based on the counter
     */
    public void startMatch(String homeTeam, String awayTeam) {
        if (matches.stream().anyMatch(m -> m.getHomeTeam().equals(homeTeam) && m.getAwayTeam().equals(awayTeam)))
            throw new MatchAlreadyExistsException("This Match is already in progress.");

        long startTime = System.currentTimeMillis() + counter++;
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

    public void clear() {
        matches.clear();
    }
}
