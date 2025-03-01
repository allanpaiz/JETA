package com.tuneup;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TemplateMode implements Mode {
    private User userProfile;
    private ProfileManager profileManager;
    private Stage primaryStage;
    private TuneUpUI tuneUpUI;

    public TemplateMode(User userProfile, ProfileManager profileManager, Stage primaryStage, TuneUpUI tuneUpUI) {
        this.userProfile = userProfile;
        this.profileManager = profileManager;
        this.primaryStage = primaryStage;
        this.tuneUpUI = tuneUpUI;
    }

    @Override
    public void handle() {
        primaryStage.setScene(createTemplateScene());
    }
    private Scene createTemplateScene() {
        // Layout
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(15));

        // Menu bar
        MenuBar menuBar = createTemplateMenuBar();
        root.setTop(menuBar);

        // Center content
        VBox centerLayout = new VBox(10);
        centerLayout.setPadding(new Insets(10));

        // Generic components
        Label titleLabel = new Label("Template Section");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        Button actionOneBtn = new Button("Action One");
        actionOneBtn.setTooltip(new Tooltip("Perform first action"));
        Button actionTwoBtn = new Button("Action Two");
        actionTwoBtn.setTooltip(new Tooltip("Perform second action"));
        Button backBtn = new Button("Return to Main");
        Label statusLabel = new Label("Ready");
        statusLabel.setStyle("-fx-text-fill: #555555;");

        // Event handlers
        actionOneBtn.setOnAction(e -> handleActionOne(centerLayout, statusLabel));
        actionTwoBtn.setOnAction(e -> handleActionTwo(centerLayout, statusLabel));

        // Main Menu Button
        backBtn.setOnAction(e -> primaryStage.setScene(new TuneUpUI().createMainMenuScene(primaryStage)));

        // Add components to layout
        centerLayout.getChildren().addAll(titleLabel, actionOneBtn, actionTwoBtn, backBtn, statusLabel);
        root.setCenter(centerLayout);

        // Scene setup
        Scene scene = new Scene(root, 450, 350);
        return scene;
    }

    private MenuBar createTemplateMenuBar() {
        MenuBar menuBar = new MenuBar();
        Menu optionsMenu = new Menu("Options");
        MenuItem itemOne = new MenuItem("Option 1");
        MenuItem itemTwo = new MenuItem("Option 2");
        optionsMenu.getItems().addAll(itemOne, itemTwo);
        menuBar.getMenus().add(optionsMenu);
        return menuBar;
    }

    private void handleActionOne(VBox layout, Label statusLabel) {
        layout.getChildren().clear();

        // Form layout
        GridPane formGrid = new GridPane();
        formGrid.setHgap(10);
        formGrid.setVgap(10);

        // Form fields
        TextField fieldOne = new TextField();
        fieldOne.setPromptText("Field 1");
        TextField fieldTwo = new TextField();
        fieldTwo.setPromptText("Field 2");

        // Add form fields to layout
        formGrid.add(new Label("Field 1:"), 0, 0);
        formGrid.add(fieldOne, 1, 0);
        formGrid.add(new Label("Field 2:"), 0, 1);
        formGrid.add(fieldTwo, 1, 1);

        // Buttons
        Button submitBtn = new Button("Submit");
        Button backBtn = new Button("Back");

        // Event handlers
        submitBtn.setOnAction(e -> {
            statusLabel.setText("Action One completed: " + fieldOne.getText() + ", " + fieldTwo.getText());
            handle();
        });

        // Back button to Template scene
        backBtn.setOnAction(e -> handle());

        // Add all the elements to the layout
        layout.getChildren().addAll(formGrid, submitBtn, backBtn, statusLabel);
    }

    private void handleActionTwo(VBox layout, Label statusLabel) {
        layout.getChildren().clear();

        // Components
        ComboBox<String> selectionBox = new ComboBox<>();
        selectionBox.getItems().addAll("Option A", "Option B", "Option C");
        selectionBox.setPromptText("Select an option");
        Button confirmBtn = new Button("Confirm");
        Button backBtn = new Button("Back");

        // Event handlers
        confirmBtn.setOnAction(e -> {
            String selected = selectionBox.getValue();
            statusLabel.setText(selected != null ? 
                "Selected: " + selected : 
                "Please make a selection");
        });

        // Back button to Template scene
        backBtn.setOnAction(e -> handle());

        // Add all the elements to the layout
        layout.getChildren().addAll(selectionBox, confirmBtn, backBtn, statusLabel);
    }
}
