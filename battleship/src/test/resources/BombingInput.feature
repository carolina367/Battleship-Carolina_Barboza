Feature: Bombing Input
  As a player
  I want to be able to enter the bomb coordinates through an input
  So I can accurately bomb ships that belong to the opposing player

  Scenario: Successfully bombing a ship
    Given a new game has started
    And I am a player in the game
    And an enemy ship is at coordinates 3, 3
    When I enter the bombing coordinates 3, 3
    Then the bombing should be a hit at coordinates 3, 3

  Scenario: Missing the bombing on empty coordinates
    Given a new game has started
    And I am a player in the game
    When I enter the bombing coordinates 1, 1
    Then the bombing should be a miss at coordinates 1, 1

  Scenario: Invalid bombing coordinates
    Given a new game has started
    And I am a player in the game
    When I enter invalid bombing coordinates 11, 11
    Then the bombing attempt should be rejected
