package com.football.scoreboard;

import com.football.scoreboard.exceptions.MatchAlreadyExistsException;
import com.football.scoreboard.exceptions.MatchNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductionTest {
    private ScoreBoard scoreboard;

    @BeforeEach
    void setUp() {
        scoreboard = new ScoreBoard();
    }

    @AfterEach
    void tearDown() {
        scoreboard.getScoreBoards().clear();
    }

    @Test
    void startMatch_shouldAddMatchToScoreboard() {
        scoreboard.startMatch("Mexico", "Canada");
        List<Match> matches = scoreboard.getScoreBoards();
        assertEquals(1, matches.size());
        assertEquals("Mexico", matches.get(0).getHomeTeam());
        assertEquals("Canada", matches.get(0).getAwayTeam());
        assertEquals(0, matches.get(0).getHomeScore());
        assertEquals(0, matches.get(0).getAwayScore());
    }

    @Test
    void updateScore_shouldUpdateMatchScore() {
        scoreboard.startMatch("Mexico", "Canada");
        scoreboard.updateScore("Mexico", "Canada", 3, 5);
        Match match = scoreboard.getScoreBoards().get(0);
        assertEquals(3, match.getHomeScore());
        assertEquals(5, match.getAwayScore());
    }

    @Test
    void finishMatch_shouldRemoveMatchFromScoreboard() {
        scoreboard.startMatch("Mexico", "Canada");
        scoreboard.finishMatch("Mexico", "Canada");
        assertTrue(scoreboard.getScoreBoards().isEmpty());
    }

    @Test
    void getSummary_shouldReturnMatchesOrderedByScoreAndTime() {
        ScoreBoard scoreboard = getScoreBoards();
        List<Match> scoreBoardList = scoreboard.getScoreBoards();

        //debug print of match times and scores for inspection
        for (Match match : scoreBoardList)
            System.out.println(match.getHomeTeam() + " vs " + match.getAwayTeam() + " - Score: " + match.getTotalScore() + " Start Time: " + match.getStartTime());

        assertEquals("Uruguay 6 - Italy 6", scoreBoardList.get(0).toString());
        assertEquals("Spain 10 - Brazil 2", scoreBoardList.get(1).toString());
        assertEquals("Mexico 0 - Canada 5", scoreBoardList.get(2).toString());
        assertEquals("Argentina 3 - Australia 1", scoreBoardList.get(3).toString());
        assertEquals("Germany 2 - France 2", scoreBoardList.get(4).toString());
    }

    private static ScoreBoard getScoreBoards() {
        ScoreBoard scoreboard = new ScoreBoard();

        scoreboard.startMatch("Mexico", "Canada");
        scoreboard.updateScore("Mexico", "Canada", 0, 5);

        scoreboard.startMatch("Spain", "Brazil");
        scoreboard.updateScore("Spain", "Brazil", 10, 2);

        scoreboard.startMatch("Germany", "France");
        scoreboard.updateScore("Germany", "France", 2, 2);

        scoreboard.startMatch("Uruguay", "Italy");
        scoreboard.updateScore("Uruguay", "Italy", 6, 6);

        scoreboard.startMatch("Argentina", "Australia");
        scoreboard.updateScore("Argentina", "Australia", 3, 1);

        return scoreboard;
    }

    @Test
    void grayTestStartMatch_nullTeamName_shouldThrowException() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            scoreboard.startMatch("", "Canada");
        });
        assertEquals("Teams' name cannot be empty.", thrown.getMessage());
    }

    @Test
    void grayTestUpdateScore_negativeHomeScore() {
        scoreboard.startMatch("Mexico", "Canada");
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            scoreboard.updateScore("Mexico", "Canada", -3, 5);
        });
        assertEquals("Scores cannot be negative.", thrown.getMessage());
    }

    @Test
    void grayTestUpdateScore_negativeAwayScore() {
        scoreboard.startMatch("Mexico", "Canada");
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            scoreboard.updateScore("Mexico", "Canada", 3, -5);
        });
        assertEquals("Scores cannot be negative.", thrown.getMessage());
    }

    @Test
    void getScore_givenTheTeamName() {
        scoreboard.startMatch("Iran", "UAE");
        int score = scoreboard.getScore("Iran");
        assertEquals(0, score);
    }

    @Test
    void testGetScore_NullHomeTeam() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            scoreboard.getScore(null);
        });
        assertEquals("Team name cannot be empty.", exception.getMessage());
    }

    @Test
    void testGetScore_EmptyHomeTeam() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            scoreboard.getScore("");
        });
        assertEquals("Team name cannot be empty.", exception.getMessage());
    }

    @Test
    void testGetScore_MatchNotFound() {
        MatchNotFoundException exception = assertThrows(MatchNotFoundException.class, () -> {
            scoreboard.getScore("UnknownTeam");
        });
        assertEquals("Match not found.", exception.getMessage());
    }

    @Test
    void grayTestUpdateScore_MatchNotFound() {
        MatchNotFoundException exception = assertThrows(MatchNotFoundException.class, () -> {
            scoreboard.updateScore("UAE", "Iran", 2, 2);
        });
        assertEquals("Match not found.", exception.getMessage());
    }

    @Test
    void grayTestFinishGame_MatchNotFound() {
        MatchNotFoundException exception = assertThrows(MatchNotFoundException.class, () -> {
            scoreboard.finishMatch("China", "Turkey");
        });
        assertEquals("Match not found.", exception.getMessage());
    }

    @Test
    void grayTestStartGame_MatchAlreadyExists() {
        scoreboard.startMatch("Mexico", "Canada");
        MatchAlreadyExistsException exception = assertThrows(MatchAlreadyExistsException.class, () -> {
            scoreboard.startMatch("Mexico", "Canada");
        });
        assertEquals("This Match is already in progress.", exception.getMessage());
    }
}