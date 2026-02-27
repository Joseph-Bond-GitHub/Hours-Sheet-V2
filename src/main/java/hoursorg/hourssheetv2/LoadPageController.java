package hoursorg.hourssheetv2;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;


public class LoadPageController {
    @FXML
    protected Label monthlbl;
    @FXML
    protected ChoiceBox<String> filechc;
    @FXML
    protected GridPane datagridPane;

    private String fileName;
    private String fileData;

    private void initialize(){
        //get array of data from text file
        //Add and populate fields of gridPane with the relevant data
    }

    private String[] getFileData(){

    }

}
