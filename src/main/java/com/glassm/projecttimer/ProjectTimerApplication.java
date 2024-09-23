// ProjectTimerApplication.java
package com.glassm.projecttimer;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ProjectTimerApplication extends Application {
    private List<Project> projects;
    private HBox projectContainer;

    @Override
    public void start(Stage primaryStage) {
        projects = new ArrayList<>();
        projectContainer = new HBox(20);

        // Create and center the title label
        Label titleLabel = new Label("Project Timer");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;"); // Set the font size of the title
        titleLabel.setAlignment(Pos.CENTER);

        // Ensure the project container expands to fill horizontal space
        projectContainer.setFillHeight(false);
        HBox.setHgrow(projectContainer, Priority.ALWAYS);

        Button addProjectButton = new Button("Add Project");
        addProjectButton.getStyleClass().add("button-info"); // Apply the info button style
        addProjectButton.setOnAction(e -> promptForProjectName());

        Button removeProjectButton = new Button("Remove Last Project");
        removeProjectButton.getStyleClass().add("button-info"); // Apply the info button style
        removeProjectButton.setOnAction(e -> removeProject());

        HBox controls = new HBox(10, addProjectButton, removeProjectButton);
        controls.setFillHeight(false);

        VBox root = new VBox(10, projectContainer, controls);
        root.setAlignment(Pos.CENTER); // Center the elements within the VBox

        Scene scene = new Scene(root, 400, 200);
        // Check if the stylesheet resource exists and add it to the scene
        String stylesheetPath = "/styles.css";
        if (getClass().getResource(stylesheetPath) != null) {
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(stylesheetPath)).toExternalForm());
        } else {
            System.err.println("Stylesheet not found: " + stylesheetPath);
        }

        primaryStage.setTitle("Project Timer");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void promptForProjectName() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("New Project");
        dialog.setHeaderText("Create a New Project");
        dialog.setContentText("Please enter the project name:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(this::addProject);
    }

    private void addProject(String name) {
        Project newProject = new Project(name);
        projects.add(newProject);
        projectContainer.getChildren().add(newProject.getView());
    }

    private void removeProject() {
        if (!projects.isEmpty()) {
            Project lastProject = projects.removeLast();
            projectContainer.getChildren().remove(lastProject.getView());
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}