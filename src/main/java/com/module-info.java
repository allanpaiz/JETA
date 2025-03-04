module com.tuneup {
    requires java.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;

    opens com.tuneup to javafx.controls;
    exports com.tuneup;
}