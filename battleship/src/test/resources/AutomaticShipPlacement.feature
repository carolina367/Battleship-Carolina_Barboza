Feature: Automatic Ship Placement
  As a player
  I want to automatically place my ships
  So I can hide my battleships faster

  Scenario: Automatically placing ships for a player
    Given a new game has started
    And I am a player in the game
    When I choose to place my ships automatically
    Then my ships should be placed on the board automatically

  Scenario: Verifying all ships are placed on the board
    Given a new game has started
    And I am a player in the game
    When I choose to place my ships automatically
    Then all my ships should be properly placed on the board
