module com.glassm.projecttimer {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.javafx;


    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;
    requires fontawesomefx;

    opens com.glassm.projecttimer to javafx.fxml;
    exports com.glassm.projecttimer;
}