import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {
//                "src/test/resources/features/GameModeSelection.feature",
//                "src/test/resources/features/RandomShipPlacement.feature",
//                      "src/test/resources/features/ManualShipPlacement.feature",
//                "src/test/resources/features/BombingInput.feature",
//                "src/test/resources/features/IdentifyBombedShip.feature",
//                "src/test/resources/features/TrackShipsLeft.feature",
//                "src/test/resources/features/SurrenderOption.feature",
//                "src/test/resources/features/PreviousBombLocations.feature",
//                "src/test/resources/features/HiddenBoards.feature"
        },
        glue = "",
        plugin = {"pretty", "html:target/cucumber-reports"},
        monochrome = true
)
public class CucumberTest {
}
