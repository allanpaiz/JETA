module com.tuneup {
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;
    requires jfugue;

    opens com.controllers to javafx.fxml;
    exports com.controllers;
    
    opens com.music to javafx.fxml;
    exports com.music;
    
    opens com.program to javafx.fxml;
    exports com.program;


}
