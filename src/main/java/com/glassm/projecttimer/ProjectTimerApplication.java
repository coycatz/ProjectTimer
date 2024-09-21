// ProjectTimerApplication.java
package com.glassm.projecttimer;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class ProjectTimerApplication extends Application {
    private List<Project> projects;
    private HBox projectContainer;

    @Override
    public void start(Stage primaryStage) {
        projects = new ArrayList<>();
        projectContainer = new HBox(10);

        // Ensure the project container expands to fill horizontal space
        HBox.setHgrow(projectContainer, Priority.ALWAYS);
        projectContainer.setPrefWidth(400);
        projectContainer.setPrefHeight(200);

        Button addProjectButton = new Button("Add Project");
        addProjectButton.setOnAction(e -> addProject());

        Button removeProjectButton = new Button("Remove Last Project");
        removeProjectButton.setOnAction(e -> removeProject());

        HBox controls = new HBox(10, addProjectButton, removeProjectButton);

        VBox root = new VBox(10, projectContainer, controls);

        Scene scene = new Scene(root, 400, 200);

        primaryStage.setTitle("Project Timer");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void addProject() {
        int projectNumber = projects.size() + 1;
        Project newProject = new Project("Project " + projectNumber);
        projects.add(newProject);
        projectContainer.getChildren().add(newProject.getView());
    }

    private void removeProject() {
        if (!projects.isEmpty()) {
            Project lastProject = projects.remove(projects.size() - 1);
            projectContainer.getChildren().remove(lastProject.getView());
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}