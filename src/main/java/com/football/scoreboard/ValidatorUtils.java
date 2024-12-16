package com.football.scoreboard;

import com.football.scoreboard.exceptions.MatchAlreadyExistsException;

import java.util.List;

public interface ValidatorUtils {
    static void validateNonNullOrEmpty(String homeTeam, String awayTeam, String errorMessage) {
        if (homeTeam == null || awayTeam == null ||
                homeTeam.trim().isEmpty() || awayTeam.trim().isEmpty())
            throw new IllegalArgumentException(errorMessage);
    }

    static void validateInProgressMatch(List<Match> matches, String homeTeam, String awayTeam, String errorMessage) {
        if (matches.stream().anyMatch(m -> m.getHomeTeam().equals(homeTeam) && m.getAwayTeam().equals(awayTeam)))
            throw new MatchAlreadyExistsException(errorMessage);
    }

    static void validatePositiveInteger(int homeTeam, int awayTeam, String errorMessage) {
        if (homeTeam < 0 || awayTeam < 0)
            throw new IllegalArgumentException(errorMessage);
    }
}