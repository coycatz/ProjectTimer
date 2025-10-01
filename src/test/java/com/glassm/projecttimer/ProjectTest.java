package com.glassm.projecttimer;

import javafx.application.Platform;
import javafx.scene.control.Label;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class ProjectTest {

    @BeforeAll
    static void initJavaFX() throws Exception {
        try {
            final CountDownLatch latch = new CountDownLatch(1);
            Platform.startup(latch::countDown);
            // wait a moment for startup
            assertTrue(latch.await(5, TimeUnit.SECONDS), "JavaFX platform failed to start in time");
        } catch (IllegalStateException alreadyStarted) {
            // JavaFX already started in this JVM; that's fine.
        }
    }

    @Test
    @DisplayName("formatTime(Long) returns '0.0' for null and correct rounding for values")
    void testFormatTimeViaReflection() throws Exception {
        Project p = new Project("Test");

        Method m = Project.class.getDeclaredMethod("formatTime", Long.class);
        m.setAccessible(true);

        assertEquals("0.0", m.invoke(p, new Object[]{null}), "Null seconds should return 0.0");

        assertEquals("0.0", m.invoke(p, 0L));
        assertEquals("0.0", m.invoke(p, 1L));
        assertEquals("0.0", m.invoke(p, 59L));

        // 60 seconds = 1 minute = 0.0 hours (rounded to 0.0)
        assertEquals("0.0", m.invoke(p, 60L));

        // 30 minutes = 0.5 hours
        assertEquals("0.5", m.invoke(p, 30L * 60));

        // 90 minutes = 1.5 hours
        assertEquals("1.5", m.invoke(p, 90L * 60));

        // 2 hours 6 minutes -> 2.1 hours (nearest 1/10)
        assertEquals("2.1", m.invoke(p, (2L * 60 + 6) * 60));

        // 10 hours -> 10.0
        assertEquals("10.0", m.invoke(p, 10L * 60 * 60));
    }

    @Test
    @DisplayName("New Project has timer label initialized to 0.0")
    void testInitialTimerLabel() {
        Project p = new Project("My Project");
        Label label = p.getTimerLabel();
        assertNotNull(label);
        assertEquals("0.0", label.getText());
    }
}
