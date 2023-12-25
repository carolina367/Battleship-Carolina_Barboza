Feature: Viewing Previous Bomb Locations

  Scenario: Viewing previous bombed locations
    Given the player has bombed previously
    When the player views the board
    Then the previous bombed locations should be visible
