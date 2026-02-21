module hoursorg.hourssheetv2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens hoursorg.hourssheetv2 to javafx.fxml;
    exports hoursorg.hourssheetv2;
}