package hoursorg.hourssheetv2;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SavePageController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}
