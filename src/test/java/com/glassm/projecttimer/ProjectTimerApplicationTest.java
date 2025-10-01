package com.glassm.projecttimer;

import javafx.application.Platform;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class ProjectTimerApplicationTest {

    @BeforeAll
    static void initJavaFX() throws Exception {
        try {
            final CountDownLatch latch = new CountDownLatch(1);
            Platform.startup(latch::countDown);
            assertTrue(latch.await(5, TimeUnit.SECONDS), "JavaFX platform failed to start in time");
        } catch (IllegalStateException alreadyStarted) {
            // already initialized
        }
    }

    @Test
    @DisplayName("start(Stage) sets up Scene and window title")
    void testStartSetsSceneAndTitle() throws Exception {
        ProjectTimerApplication app = new ProjectTimerApplication();

        java.util.concurrent.atomic.AtomicReference<Stage> stageRef = new java.util.concurrent.atomic.AtomicReference<>();
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                Stage stage = new Stage();
                app.start(stage);
                stageRef.set(stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                latch.countDown();
            }
        });
        assertTrue(latch.await(5, TimeUnit.SECONDS), "start(Stage) did not complete in time");

        Stage stage = stageRef.get();
        assertNotNull(stage, "Stage should have been initialized on FX thread");
        assertEquals("Project Timer", stage.getTitle());
        assertNotNull(stage.getScene());
        assertNotNull(stage.getScene().getRoot());
    }
}
