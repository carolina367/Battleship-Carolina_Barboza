Feature: Manual Ship Placement

  Scenario: Manually placing a ship
    Given the player is on the ship placement screen
    When the player chooses to place a ship at coordinates "A1" vertically
    Then the ship should be placed at "A1" vertically on the board
