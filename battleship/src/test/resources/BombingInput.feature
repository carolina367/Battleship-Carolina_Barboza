Feature: Entering Bomb Coordinates

  Scenario: Bombing a coordinate
    Given the player is in the bombing phase
    When the player enters the coordinates "B2" for bombing
    Then a bomb should be placed at "B2"
