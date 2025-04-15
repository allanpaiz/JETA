module tuneup {
    // JavaFX requirements
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    
    // Java standard modules
    requires java.logging;
    requires java.desktop;  // For MIDI support
    requires java.sql;      // For database access if needed
    
    // Jackson JSON processing
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;
    
    // Testing
    requires junit;
    
    // Export packages for use by other modules
    exports tuneup;
    exports controllers;
    
    // Allow FXML to access your controllers via reflection
    opens controllers to javafx.fxml, org.junit;
    
    // Combine all opens statements for tuneup package
    opens tuneup to com.fasterxml.jackson.databind, org.junit;
}