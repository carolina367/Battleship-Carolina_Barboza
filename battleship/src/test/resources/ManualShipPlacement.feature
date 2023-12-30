Feature: Manual Ship Placement

  Scenario: Manually placing a ship
    Given the player is on the ship placement screen
    When the player chooses to place a ship at coordinates 0 and 0 vertically
    Then the ship should be placed at 0 and 0 vertically on the board
