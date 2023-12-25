Feature: Game Mode Selection

  Scenario: Choosing a new game mode
    Given the game is at the start screen
    When the player chooses to start a "multiplayer" game
    Then a new "multiplayer" game should start

  Scenario: Choosing AI game mode
    Given the game is at the start screen
    When the player chooses to start an "AI" game
    Then a new "AI" game should start
