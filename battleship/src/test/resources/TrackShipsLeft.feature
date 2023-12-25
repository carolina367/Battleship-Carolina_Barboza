Feature: Tracking Ships Left

  Scenario: Viewing ships remaining
    Given the game is in progress
    When the player checks the number of ships left
    Then the player should see the number of ships remaining for both players
