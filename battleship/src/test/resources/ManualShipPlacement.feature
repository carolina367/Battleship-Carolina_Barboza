Feature: Manual Ship Placement
  As a player
  I want to choose the location of the ships
  So that I can properly hide my battleships from the other player

  Scenario: Manually placing a ship on the board
    Given a new game has started
    And I am a player in the game
    When I choose to place a ship manually at specific coordinates
    Then the ship should be placed at those coordinates on the board

  Scenario: Invalid ship placement
    Given a new game has started
    And I am a player in the game
    When I choose an invalid location to place a ship
    Then the placement should be rejected
