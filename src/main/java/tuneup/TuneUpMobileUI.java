package tuneup;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import controllers.LoginViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 * Mobile-optimized UI for TuneUp music education application
 */
public class TuneUpMobileUI extends Application {
    private static final Logger logger = Logger.getLogger(TuneUpMobileUI.class.getName());
    
    @Override
    public void start(Stage stage) {
        try {
            // Create the application facade
            TuneUp facade = new TuneUp();
            
            // Get resources using proper resource loading
            URL fxmlUrl = getClass().getResource("/fxml/login.fxml");
            URL cssUrl = getClass().getResource("/css/styles.css");
            
            if (fxmlUrl == null) {
                throw new IOException("Cannot find login.fxml resource");
            }
            
            // Load the FXML
            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Parent root = loader.load();
            
            // Get controller and initialize it
            LoginViewController controller = loader.getController();
            controller.initialize(facade);
            controller.setStage(stage);
            
            // Create scene with CSS styling if available
            Scene scene = new Scene(root, 390, 600);
            if (cssUrl != null) {
                scene.getStylesheets().add(cssUrl.toExternalForm());
            }
            
            // Set up and show the stage
            stage.setTitle("TuneUp");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
            
            logger.info("TuneUp application started successfully");
            
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error starting application", e);
            showErrorAlert("Application Error", "Failed to start the application", e.getMessage());
        }
    }
    
    private void showErrorAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}