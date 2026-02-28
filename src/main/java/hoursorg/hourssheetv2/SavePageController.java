package hoursorg.hourssheetv2;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.time.LocalDate;
import java.util.Objects;


public class SavePageController {
    @FXML
    protected TextField hoursfld;
    protected TextArea descriptionarea;
    protected ChoiceBox<String> filechc;
    protected Button savebtn;
    protected Button toLoadScreenbtn;
    protected Label popuplbl;

    private final PauseTransition removePopUplbl = new PauseTransition();
    private FileOperations fileOperation;

    @FXML
    public void initialize() {
        initializefilechc();
        removePopUplbl.setDuration(Duration.seconds(3));
        removePopUplbl.setOnFinished(event1 -> {
            popuplbl.setVisible(false);
        });
    }

    @FXML
    protected void onsavebtnclick() {
        String fileName = getSelectedFileName();
        String content = "";
        content = fieldsToTextLine();
        if (descriptionarea.getText().isEmpty() || hoursfld.getText().isEmpty()){
            popuplbl.setText("Error: One or more \nfields empty");
            popuplbl.setVisible(true);
            removePopUplbl.play();
        }else{
            if (fileOperation.writeToFile(content, fileName)){
                popuplbl.setText("Saving successful");
                popuplbl.setVisible(true);
                removePopUplbl.play();
            }else{
                popuplbl.setText("Error: Unknown");
                popuplbl.setVisible(true);
                removePopUplbl.play();
            }
        }
    }

    private String getSelectedFileName(){
        return filechc.getSelectionModel().getSelectedItem();
    }

    private void initializefilechc() {
        filechc.getItems().addAll(fileOperation.getFileNamesInDataDirectory());
        filechc.setStyle("-fx-font-size: 16");
        filechc.getSelectionModel().select(0);
    }

    private String fieldsToTextLine(){
        //If there are multiple lines in description field remove these and replace with character. Then when reading do
        //the inverse??
        String textLine;
        LocalDate date = LocalDate.now();
        textLine = date.toString();
        textLine += "•" + descriptionarea.getText();
        if (isDouble(hoursfld.getText())){
            textLine += "•" + hoursfld.getText();
            return textLine;
        }else{
            popuplbl.setText("ERROR: Cannot save non-numerical hours");
            popuplbl.setVisible(true);
            removePopUplbl.play();
        }
        return "";
    }

    private Boolean isDouble(String num){
        //check that the value of hours entered is a double
        //first strip the string value
        try{
            Double.parseDouble(num.strip());
            return true;
        }catch(NumberFormatException e){
            return false;
        }
    }
    @FXML
    protected void ontoLoadScreenbtnClick() throws Exception {
        switchScene("load-page.fxml");
    }
    private void switchScene(String fxmlFile) throws Exception {
        Parent newRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlFile)));
        Stage window = (Stage) toLoadScreenbtn.getScene().getWindow();  // Get current stage
        Scene newScene = new Scene(newRoot);
        window.setScene(newScene);
        window.show();
    }
}

