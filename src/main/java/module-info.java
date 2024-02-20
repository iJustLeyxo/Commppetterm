module commppetterm {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;
    requires com.gluonhq.charm.glisten;
    requires org.jetbrains.annotations;
    opens commppetterm to javafx.fxml;
    exports commppetterm;
    exports commppetterm.gui;
    opens commppetterm.gui to javafx.fxml;
    exports commppetterm.gui.page;
    opens commppetterm.gui.page to javafx.fxml;
    exports commppetterm.util;
    opens commppetterm.util to javafx.fxml;
}

