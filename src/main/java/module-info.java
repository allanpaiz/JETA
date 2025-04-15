module tuneup {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    
    // Java standard modules
    requires java.logging;
    requires java.desktop;
    requires java.sql;
    
    // Jackson JSON processing
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;
    
    // Testing dependencies - uncomment these for tests to work
    //requires org.junit.jupiter.api;
    //requires org.hamcrest;  // JUnit 4 depends on Hamcrest
    
    // Export packages
    exports tuneup;
    exports controllers;
    
    // Open packages for reflection
    opens controllers to javafx.fxml;
    opens tuneup to com.fasterxml.jackson.databind;
}