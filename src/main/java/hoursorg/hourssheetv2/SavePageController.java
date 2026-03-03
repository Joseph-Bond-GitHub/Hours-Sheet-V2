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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;


public class SavePageController {
    @FXML protected TextField hoursfld;
    @FXML protected TextArea descriptionarea;
    @FXML protected ComboBox<String> filecmb;
    @FXML protected Button savebtn;
    @FXML protected Button toLoadScreenbtn;
    @FXML protected Label popuplbl;
    @FXML protected ListView<String> tagslsv;

    private final PauseTransition removePopUplbl = new PauseTransition();
    private FileOperations fileOperation;

    @FXML
    public void initialize() {
        this.fileOperation = new FileOperations();
        initializefilechc();
        populateCategories();
        removePopUplbl.setDuration(Duration.seconds(3));
        removePopUplbl.setOnFinished(event1 -> {
            popuplbl.setVisible(false);
        });
    }
    private void initializefilechc() {
        filecmb.setStyle("-fx-font-size: 16");
        filecmb.getItems().addAll(fileOperation.getFileNamesInDataDirectory());
        filecmb.getSelectionModel().select(0);
    }
    private void populateCategories() {
        tagslsv.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        List<String> tags = fileOperation.getTags();
        tagslsv.getItems().addAll(tags);
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
        return filecmb.getSelectionModel().getSelectedItem();
    }

    private List<String> populatefilechc() {
        List<String> list = new ArrayList<>();
        //add the default value to the list as the first item
        list.add(getCurrentMonthFile());
        //get the names of items from the directory "Data"
        try {
            Path dataDirectoryPath = Paths.get("src/main/Data");
            for (Path filePath : Files.newDirectoryStream(dataDirectoryPath)){
                if(!filePath.getFileName().toString().equals(getCurrentMonthFile())){
                    list.add(filePath.getFileName().toString());
                }
            }
        }catch(Exception e){
            popuplbl.setText("ERROR: cannot get files in directory");
            popuplbl.setVisible(true);
            removePopUplbl.play();
        }

        return list;
    }

    private String getCurrentMonthFile(){
        LocalDate date = LocalDate.now();
        String monthFile = "";
        monthFile = date.getYear() + "-";
        int monthLength = Integer.toString(date.getMonthValue()).length();
        monthFile = (monthLength == 1) ? monthFile + "0" + date.getMonthValue() : monthFile + date.getMonthValue();
        monthFile += ".txt";
        return monthFile;
    }
    private Boolean writeToFile(String content, String fileName){
        Path dataDirectory = Paths.get("src/main/Data/" + fileName);
        try{
            OutputStream output = new BufferedOutputStream(Files.newOutputStream(dataDirectory, CREATE, APPEND));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output));
            writer.write(content);
            writer.newLine();
            writer.flush();
            writer.close();
            //add more fine-grained exception handling
        }catch(Exception e){
            return false;
        }
        return true;
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

