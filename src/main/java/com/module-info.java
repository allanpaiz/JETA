module com.tuneup {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.tuneup to javafx.fxml;
    exports com.tuneup;

}
