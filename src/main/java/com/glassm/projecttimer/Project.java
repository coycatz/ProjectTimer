// Project.java
package com.glassm.projecttimer;

import javafx.animation.AnimationTimer;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.text.DecimalFormat;

public class Project {
    private final VBox projectView;
    private String title;
    private Long startTime;
    private Long elapsedTime;
    private AnimationTimer timer;
    private final Label titleLabel;
    private final Label timerLabel;
    private final Button timerButton;

    private static Project currentRunningProject;

    public Project(String title) {
        this.title = title;

        this.timerLabel = new Label("0.0");
        timerLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold");

        this.titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-size: 16px;");

        this.timerButton = new Button("Start");
        timerButton.setStyle("-fx-border-color: green; -fx-border-width: 4px;");


        // Initialize the reset icon
        // Initialize the reset label as a placeholder for an icon
        Label resetLabel = new Label("\u27F3"); // Unicode character for refresh
        resetLabel.setStyle("-fx-font-size: 20px;");

        resetLabel.setOnMouseClicked(event -> resetTimer());

        // Add labels and icon to the project view
        HBox nameAndReset = new HBox(10, titleLabel, resetLabel);
        nameAndReset.setAlignment(Pos.CENTER_LEFT);

        projectView = new VBox(10, nameAndReset, timerLabel, this.timerButton);
        projectView.setAlignment(Pos.CENTER);
        projectView.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-padding: 10;");

        // Add a border to the project view

        this.startTime = null;
        this.elapsedTime = 0L;

        initTimer();
        setupButtonAction();
    }

    private void initTimer() {
        this.timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (startTime == null) {
                    startTime = now;
                }

                Long elapsedSeconds = (now - startTime) / 1_000_000_000;

                int seconds = elapsedSeconds.intValue();
                Integer minutes = seconds / 60;

                timerLabel.setText(formatTime(elapsedSeconds));
            }
        };
    }

    private void resetTimer() {
        timerLabel.setText("0.0");
    }

    // Helper method to convert elapsed seconds to formatted time string
    private String formatTime(Long seconds) {
        if (seconds == null) {
            return "0.0"; // Default value if elapsedSeconds is null
        }

        long totalMinutes = seconds / 60; // Convert seconds to minutes
        long hours = totalMinutes / 60;
        double decimalMinutes = (totalMinutes % 60) / 60.0;
        double totalHours = hours + decimalMinutes;

        // Format totalHours to the nearest 1/10
        DecimalFormat df = new DecimalFormat("#0.0");
        return df.format(totalHours);
    }

    private void setupButtonAction() {
        timerButton.setOnAction(e -> {
            if (timerButton.getText().equals("Start")) {
                if (currentRunningProject != null) {
                    currentRunningProject.stopTimer();
                }
                currentRunningProject = this;
                startTimer();
            } else {
                stopTimer();
                currentRunningProject = null;
            }
        });
    }
    private void startTimer() {
        startTime = null;
        timer.start();
        timerButton.setText("Stop");
        timerButton.setStyle("-fx-border-color: red; -fx-border-width: 4px;");
    }

    private void stopTimer() {
        timer.stop();
        timerButton.setText("Start");
        timerButton.setStyle("-fx-border-color: green; -fx-border-width: 4px;");
        elapsedTime += (System.nanoTime() - startTime) / 1_000_000_000;
    }

    public VBox getView() {
        return this.projectView;
    }

    public Long getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(Long elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public Label getTimerLabel() {
        return timerLabel;
    }
}