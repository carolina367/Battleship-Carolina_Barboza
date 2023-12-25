Feature: Hidden Boards

  Scenario: Keeping the board hidden from the opponent
    Given the game is in progress
    When the opponent tries to view the player's board
    Then the board should remain hidden from the opponent
