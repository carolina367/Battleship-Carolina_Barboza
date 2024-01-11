Feature: Game Mode Selection
  As a player
  I want to choose a new game against AI or local multiplayer
  So I can play my preferred way

  Scenario: Choosing single-player mode
    Given the game is started
    When I choose single-player mode
    Then the game should start in single-player mode

  Scenario: Choosing multiplayer mode
    Given the game is started
    When I choose multiplayer mode
    Then the game should start in multiplayer mode
