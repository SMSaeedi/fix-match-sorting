# Live Football World Cup Scoreboard Library

## Description
A library for managing live football matches, providing operations to start matches, update scores, finish matches, 
and get a score boards of ongoing matches.

## Features
1. Start a new match.
2. Update the score of an ongoing match.
3. Finish a match.
4. Get a summary of matches in progress, ordered by total score and start time.

## Design Principles
- **SOLID**: Adheres to single responsibility, open-closed, and interface segregation principles.
- **TDD**: Developed using test-driven development.
- **Clean Code**: Focused on readability and maintainability.

## Acceptance Criteria
- Matches are uniquely identified by a combination of the home team and away team.
- When a new match is started, it is added to the scoreboard with an initial score of 0 – 0.
- Each match records the following parameters:
  - Home team name.
  - Away team name.
  - Initial score of 0 for both teams.
  - Start time to ensure uniqueness, even for matches started at the same millisecond.
- Scores are updated as absolute values for both home and away teams.
- The update score feature receives a pair of absolute scores (home team score and away team score).
- A match in progress can be finished, and this removes the match from the scoreboard.
- Matches are displayed in a summary in the following order:
  - Descending order by total score (sum of home and away scores).
  - For matches with the same total score, they are ordered by the most recent start time (latest match first).

## How to Run
1. Clone the repository.
2. Run `ScoreBoardTest` for unit tests.

## Clean and Build the Project
- mvn clean install

## Package the Project
- mvn package