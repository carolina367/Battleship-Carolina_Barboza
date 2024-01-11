import com.model.*;
import com.model.Game.GameMode;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import java.util.Scanner;

import static org.junit.Assert.*;

public class StepWriteOut {
    private Game game;
    private GameMode selectedMode;
    private GameParticipant player;
    private boolean invalidPlacementResult;
    private boolean bombingResultValid;
    private Board.BombingResult lastBombingResult;

    private int playerShipsLeft;
    private int opponentShipsLeft;

    // Step definitions for "Game Mode Selection" feature
    @Given("the game is started")
    public void the_game_is_started() {
        game = new Game();
    }

    @When("I choose single-player mode")
    public void i_choose_single_player_mode() {
        selectedMode = GameMode.SINGLE_PLAYER;
        game.startGame(selectedMode);
    }

    @When("I choose multiplayer mode")
    public void i_choose_multiplayer_mode() {
        selectedMode = GameMode.MULTIPLAYER;
        game.startGame(selectedMode);
    }

    @Then("the game should start in single-player mode")
    public void the_game_should_start_in_single_player_mode() {
        assertEquals(GameMode.SINGLE_PLAYER, game.getGameMode());
    }

    @Then("the game should start in multiplayer mode")
    public void the_game_should_start_in_multiplayer_mode() {
        assertEquals(GameMode.MULTIPLAYER, game.getGameMode());
    }

    // Step definitions for "Automatic Ship Placement" feature
    @Given("a new game has started")
    public void a_new_game_has_started() {
        game = new Game();
        // Initialize the game with necessary setup
    }

    @Given("I am a player in the game")
    public void i_am_a_player_in_the_game() {
        player = new Player("Player 1");
        // You can also mock the player if needed
    }

    @When("I choose to place my ships automatically")
    public void i_choose_to_place_my_ships_automatically() {
        player.placeShips(null, true); // Assuming true represents automatic placement
    }

    @Then("my ships should be placed on the board automatically")
    public void my_ships_should_be_placed_on_the_board_automatically() {
        assertNotNull(player.getBoard());
        // Additional checks can be added to verify ship placement
    }

    @Then("all my ships should be properly placed on the board")
    public void all_my_ships_should_be_properly_placed_on_the_board() {
        assertTrue(player.getBoard().areAllShipsPlaced());
        // This assumes a method 'areAllShipsPlaced()' exists in Board class
    }

    // Step definitions for "Manual Ship Placement" feature
    @When("I choose to place a ship manually at specific coordinates")
    public void i_choose_to_place_a_ship_manually_at_specific_coordinates() {
        // Assuming the player has a method to manually place ships
        // This can be a mocked interaction as actual user input can't be simulated in tests
        boolean isPlaced = player.getBoard().placeShip(Ship.ShipType.DESTROYER, 5, 5, true);
        assertTrue(isPlaced);
    }

    @Then("the ship should be placed at those coordinates on the board")
    public void the_ship_should_be_placed_at_those_coordinates_on_the_board() {
        // Verify that the ship is placed at the specified coordinates
        Tile[][] grid = player.getBoard().getGrid();
        assertNotNull(grid[5][5].getShip());
        assertEquals(Ship.ShipType.DESTROYER, grid[5][5].getShip().getType());
    }

    @When("I choose an invalid location to place a ship")
    public void i_choose_an_invalid_location_to_place_a_ship() {
        // Attempt to place a ship at an invalid location
        // For instance, outside the bounds of the board
        // Store the result instead of directly asserting it
        invalidPlacementResult = player.getBoard().placeShip(Ship.ShipType.CRUISER, 10, 10, true);
    }

    @Then("the placement should be rejected")
    public void the_placement_should_be_rejected() {
        // Verify that the ship placement was rejected
        assertFalse(invalidPlacementResult);
    }

    // Step definitions for "Bombing Input" feature
    @Given("an enemy ship is at coordinates {int}, {int}")
    public void an_enemy_ship_is_at_coordinates(int x, int y) {
        // Place an enemy ship at the specified coordinates for testing
        player.getBoard().placeShip(Ship.ShipType.DESTROYER, x, y, true);
    }

    @When("I enter the bombing coordinates {int}, {int}")
    public void i_enter_the_bombing_coordinates(int x, int y) {
        // Simulate bombing at the given coordinates
        player.getBoard().bombAt(x, y);
    }

    @Then("the bombing should be a hit at coordinates {int}, {int}")
    public void the_bombing_should_be_a_hit_at_coordinates(int x, int y) {
        // Verify that the bombing was a hit
        Tile tile = player.getBoard().getGrid()[x][y];
        assertTrue(tile.isHit() && tile.isOccupied());
    }

    @Then("the bombing should be a miss at coordinates {int}, {int}")
    public void the_bombing_should_be_a_miss_at_coordinates(int x, int y) {
        // Verify that the bombing was a miss
        Tile tile = player.getBoard().getGrid()[x][y];
        assertTrue(tile.isMissedBomb());
    }

    @When("I enter invalid bombing coordinates {int}, {int}")
    public void i_enter_invalid_bombing_coordinates(int x, int y) {
        // Simulate an attempt to bomb at invalid coordinates
        // This step might be left empty if the validation is done in the bombAt method
        bombingResultValid = player.getBoard().bombAt(x, y).isHit();
    }

    @Then("the bombing attempt should be rejected")
    public void the_bombing_attempt_should_be_rejected() {
        // Verify that the bombing attempt at invalid coordinates is rejected
        // This can be checked by validating the return value of bombAt or any error handling
        assertFalse(bombingResultValid);
    }

    @Given("an enemy ship is fully placed at coordinates {int},{int} to {int},{int}")
    public void an_enemy_ship_is_fully_placed_at_coordinates(int startX, int startY, int endX, int endY) {
        // Place a ship fully covering the specified coordinates
        player.getBoard().placeShip(Ship.ShipType.CRUISER, startX, startY, startX == endX);
    }

    @Given("an enemy ship is partially placed at coordinates {int},{int} to {int},{int}")
    public void an_enemy_ship_is_partially_placed_at_coordinates(int startX, int startY, int endX, int endY) {
        // Place a ship partially covering the specified coordinates
        player.getBoard().placeShip(Ship.ShipType.BATTLESHIP, startX, startY, startX == endX);
    }

    @When("I bomb each coordinate from {int},{int} to {int},{int}")
    public void i_bomb_each_coordinate_from_to(int startX, int startY, int endX, int endY) {
        // Bomb each coordinate in the specified range
        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
                lastBombingResult = player.getBoard().bombAt(x, y);
            }
        }
    }

    // Adjusted step definition for bombing and sinking a ship
    @Then("I should be informed that a Cruiser has been sunk")
    public void i_should_be_informed_that_a_cruiser_has_been_sunk() {
        assertNotNull(lastBombingResult);
        assertEquals(Ship.ShipType.CRUISER, lastBombingResult.getSunkShip());
    }

    // New step definition for "I bomb the coordinates 4,4 and 4,5"
    @When("I bomb the coordinates {int},{int} and {int},{int}")
    public void i_bomb_the_coordinates_and(int x1, int y1, int x2, int y2) {
        lastBombingResult = player.getBoard().bombAt(x1, y1);
        lastBombingResult = player.getBoard().bombAt(x2, y2);
    }

    @Then("I should not receive a message about a sunk ship")
    public void i_should_not_receive_a_message_about_a_sunk_ship() {
        // Verify that the last bombing result does not indicate a ship has been sunk
        assertNotNull(lastBombingResult);
        assertNull(lastBombingResult.getSunkShip());
    }


}
