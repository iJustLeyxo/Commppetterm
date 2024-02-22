module commppetterm {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;
    requires com.gluonhq.charm.glisten;
    requires org.jetbrains.annotations;

    exports commppetterm;
    opens commppetterm to javafx.fxml;
    exports commppetterm.gui;
    opens commppetterm.gui to javafx.fxml;
    exports commppetterm.gui.page;
    opens commppetterm.gui.page to javafx.fxml;
    exports commppetterm.exception.gui;
    opens commppetterm.exception.gui to javafx.fxml;
}

