Feature: Identify Bombed Ship

  Scenario: Identifying a fully bombed ship
    Given a ship is fully bombed
    When the bombing phase ends
    Then the player should be notified about the type of the bombed ship
