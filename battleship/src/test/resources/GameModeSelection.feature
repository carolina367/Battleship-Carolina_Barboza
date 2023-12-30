Feature: Game Mode Selection

  Scenario: Choosing a new game mode
    Given the game is at the start screen
    When the player chooses to start a "multiplayer" game
    Then a new "multiplayer" game should start

  Scenario: Choosing Single Player game mode
    Given the game is at the start screen
    When the player chooses to start a "single player" game
    Then a new "single player" game should start
