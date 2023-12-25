Feature: Surrender Option

  Scenario: Choosing to surrender
    Given the game is in progress
    When the player chooses to surrender
    Then the game should end and the opponent should win
