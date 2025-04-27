package controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tuneup.DataLoader;
import tuneup.Song;
import tuneup.TuneUp;
import tuneup.User;

/**
 * Controller for the profile view.
 * Handles the display and interaction logic for the user's profile page.
 */
public class ProfileViewController {
    private static final Logger logger = Logger.getLogger(ProfileViewController.class.getName());
    
    @FXML private Label usernameLabel;
    @FXML private Label roleLabel;
    @FXML private Label emailLabel;
    @FXML private Label experienceLevelLabel;
    @FXML private Label memberSinceLabel;
    @FXML private Label songsCreatedLabel;
    @FXML private Label lessonsCompletedLabel;
    @FXML private Label statusLabel;
    @FXML private Circle profilePictureCircle;
    
    private Stage stage;
    private TuneUp facade;
    private ProfileController profileController;
    private User currentUser;
    
    /**
     * Initializes the controller with the application facade and the current user.
     * 
     * @param facade The application facade providing access to core functionality.
     * @param user The current user whose profile is being displayed.
     */
    public void initialize(TuneUp facade, User user) {
        this.facade = facade;
        this.currentUser = user;
        this.profileController = new ProfileController(facade);
        
        if (user == null) {
            logger.severe("Attempted to initialize ProfileViewController with null user");
// In a real app, you might want to redirect back to login
            return;
        }
        
        displayUserData();
        logger.info("ProfileViewController initialized for user: " + user.getUsername());
    }
    
    /**
     * Displays the user's data in the profile view.
     * Populates labels and other UI elements with user information.
     */
    private void displayUserData() {
// Basic user data
        usernameLabel.setText(currentUser.getUsername());
        roleLabel.setText(currentUser.getRole().toString());
        emailLabel.setText(currentUser.getEmail());
        experienceLevelLabel.setText(currentUser.getExperienceLevel().toString());

        // Member since - this would need to be added to User class
        memberSinceLabel.setText("April 2023"); // Placeholder
        
// Count songs created by this user
        try {
            List<Song> allSongs = DataLoader.loadSongs();
            long songsCreated = allSongs.stream()
                .filter(song -> song.getCreatorId().equals(currentUser.getId()))
                .count();
            songsCreatedLabel.setText(String.valueOf(songsCreated));
        } catch (Exception e) {
            songsCreatedLabel.setText("0");
            logger.log(Level.WARNING, "Error loading songs", e);
        }
        
// Lessons completed - would come from a user stats system
        lessonsCompletedLabel.setText("0"); // Placeholder

// Load profile picture
        String profilePicturePath = currentUser.getProfilePicturePath();
        if (profilePicturePath != null && !profilePicturePath.isEmpty()) {
            profilePictureCircle.setFill(new ImagePattern(new Image(profilePicturePath)));
        }
    }
    
    /**
     * Sets the application stage.
     * 
     * @param stage The primary stage of the application.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
    /**
     * Handles the "Edit Profile" button click.
     * Opens a dialog to allow the user to edit their profile information.
     */
    @FXML
    public void handleEditProfile() {
// Create a dialog for editing profile
        Dialog<User> dialog = new Dialog<>();
        dialog.setTitle("Edit Profile");
        dialog.setHeaderText("Update your profile information");
        
// Set the button types
        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);
        
// Create the fields grid
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new javafx.geometry.Insets(20, 150, 10, 10));
        
        TextField usernameField = new TextField(currentUser.getUsername());
        TextField emailField = new TextField(currentUser.getEmail());
        ComboBox<String> experienceLevelCombo = new ComboBox<>();
        experienceLevelCombo.getItems().addAll("BEGINNER", "INTERMEDIATE", "ADVANCED");
        experienceLevelCombo.setValue(currentUser.getExperienceLevel().toString());
        
        grid.add(new Label("Username:"), 0, 0);
        grid.add(usernameField, 1, 0);
        grid.add(new Label("Email:"), 0, 1);
        grid.add(emailField, 1, 1);
        grid.add(new Label("Experience Level:"), 0, 2);
        grid.add(experienceLevelCombo, 1, 2);
        
        dialog.getDialogPane().setContent(grid);
        
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
// Update only certain fields
                User updatedUser = new User(
                    currentUser.getId(),
                    usernameField.getText(),
                    currentUser.getPassword(),
                    emailField.getText(),
                    currentUser.getRole(),
                    tuneup.ExperienceLevel.valueOf(experienceLevelCombo.getValue())
                );
                return updatedUser;
            }
            return null;
        });
        
        Optional<User> result = dialog.showAndWait();
        
        result.ifPresent(updatedUser -> {
            boolean success = profileController.updateUserProfile(currentUser.getId(), updatedUser);
            if (success) {
// Update the current user and display
                currentUser = updatedUser;
                displayUserData();
                statusLabel.setText("Profile updated successfully!");
                statusLabel.setTextFill(Color.GREEN);
            } else {
                statusLabel.setText("Failed to update profile");
                statusLabel.setTextFill(Color.RED);
            }
        });
    }
    
    /**
     * Handles the "Change Password" button click.
     * Opens a dialog to allow the user to change their password.
     */
    @FXML
    public void handleChangdial// Set the button types
ood){/Cate a dialog for changing password
        Dialog<String[]> dialog = new Dialog<>();
        dialog.setTitle("Change Password");
        dialog.setHeaderText("Enter your current password and a new password");
        
// Set the button types
        ButtonType saveButtonType = new ButtonType("Change Password", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);
        
// Create the password fields grid
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new javafx.geometry.Insets(20, 150, 10, 10));
        
        PasswordField currentPasswordField = new PasswordField();
        PasswordField newPasswordField = new PasswordField();
        PasswordField confirmPasswordField = new PasswordField();
        
        grid.add(new Label("Current Password:"), 0, 0);
        grid.add(currentPasswordField, 1, 0);
        grid.add(new Label("New Password:"), 0, 1);
        grid.add(newPasswordField, 1, 1);
        grid.add(new Label("Confirm Password:"), 0, 2);
        grid.add(confirmPasswordField, 1, 2);
        
        dialog.getDialogPane().setContent(grid);
        
// Convert the result when the change password button is clicked
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                return new String[] {
                    currentPasswordField.getText(),
                    newPasswordField.getText(),
                    confirmPasswordField.getText()
                };
            }
            return null;
        });
        
        Optional<String[]> result = dialog.showAndWait();
        
        result.ifPresent(passwords -> {
            String currentPassword = passwords[0];
            String newPassword = passwords[1];
            String confirmPassword = passwords[2];
            
            if (!newPassword.equals(confirmPassword)) {
                statusLabel.setText("New passwords don't match");
                statusLabel.setTextFill(Color.RED);
                return;
            }
            
            boolean success = profileController.changePassword(
                currentUser.getId(), currentPassword, newPassword);
            
            if (success) {
                statusLabel.setText("Password changed successfully!");
                statusLabel.setTextFill(Color.GREEN);
            } else {
                statusLabel.setText("Failed to change password");
                statusLabel.setTextFill(Color.RED);
            }
        });
    }
    
    /**
     * Handles the "Change Profile Picture" button click.
     * Opens a file chooser to allow the user to select a new profile picture.
     */
    @FXML
    public void handleChangeProfilePicture() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Profile Picture");
// Set initial directory to the images folder
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

// Set initial directory to the images folder
        File imagesFolder = new File("images");
        if (imagesFolder.exists() && imagesFolder.isDirectory()) {
            fileChooser.setInitialDirectory(imagesFolder);
        } else {
            logger.warning("Images folder not found. Defaulting to user home directory.");
            fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        }

        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            String imagePath = selectedFile.toURI().toString();

// Update the profile picture in the UI
            profilePictureCircle.setFill(new ImagePattern(new Image(imagePath)));

// Save the profile picture path to the user profile
            currentUser.setProfilePicturePath(imagePath);
            boolean success = profileController.updateUserProfile(currentUser.getId(), currentUser);

            if (success) {
                statusLabel.setText("Profile picture updated successfully!");
                statusLabel.setTextFill(Color.GREEN);
            } else {
                statusLabel.setText("Failed to update profile picture");
                statusLabel.setTextFill(Color.RED);
            }
        }
    }
    
    /**
     * Navigate back to home screen
     */
    @FXML
    public void navigateToHome() {
        try {
// Load the home screen using resource path
            URL fxmlUrl = getClass().getResource("/fxml/home.fxml");
            URL cssUrl = getClass().getResource("/css/styles.css");
            
            if (fxmlUrl == null) {
                throw new IOException("Cannot find home.fxml resource");
            }
            
// Load the FXML
            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Parent root = loader.load();
            
// Get controller and initialize it
            HomeViewController controller = loader.getController();
            controller.initialize(new HomeController(facade));
            controller.setStage(stage);
            controller.displayUserData(currentUser);
            
// Create and set scene
            Scene scene = new Scene(root, 390, 600);
            if (cssUrl != null) {
                scene.getStylesheets().add(cssUrl.toExternalForm());
            }
            
            stage.setScene(scene);
            stage.setTitle("TuneUp Home");
            
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Error navigating to home", ex);
            showError("Navigation Error", "Could not return to home screen");
        }
    }
    
    /**
     * Displays an error dialog with the specified title and message.
     * 
     * @param title The title of the error dialog.
     * @param message The message to display in the error dialog.
     */
    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}