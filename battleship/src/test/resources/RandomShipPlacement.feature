Feature: Random Ship Placement

  Scenario: Randomly placing ships
    Given the player is on the ship placement screen
    When the player chooses to randomly place ships
    Then the ships should be randomly placed on the board
