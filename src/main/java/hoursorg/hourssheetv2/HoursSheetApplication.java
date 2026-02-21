package hoursorg.hourssheetv2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HoursSheetApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HoursSheetApplication.class.getResource("save-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hours Sheet");
        stage.setWidth(600);
        stage.setHeight(485);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}
