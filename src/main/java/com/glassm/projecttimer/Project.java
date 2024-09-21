// Project.java
package com.glassm.projecttimer;

import javafx.animation.AnimationTimer;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class Project {
    private Long startTime;
    private Long elapsedTime;
    private AnimationTimer timer;
    private final Label titleLabel;
    private final Label timerLabel;
    private final Button timerButton;

    private static Project currentRunningProject;

    public Project(String title) {
        this.titleLabel = new Label(title);
        this.timerLabel = new Label();
        this.timerButton = new Button("Start");
        this.startTime = null;
        this.elapsedTime = 0L;

        timerButton.setStyle("-fx-border-color: green; -fx-border-width: 4px;");

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

                timerLabel.setText(elapsedSeconds + " s");
            }
        };
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
        return new VBox(20, titleLabel, timerLabel, timerButton);
    }
}