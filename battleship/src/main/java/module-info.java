module com.example.battleship {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.battleship to javafx.fxml;

    // Export the com.model package
    exports com.model;

    // Continue to export com.example.battleship if other classes in this package need to be accessed externally
    exports com.example.battleship;
}
