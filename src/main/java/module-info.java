module commppetterm {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;
    requires com.gluonhq.charm.glisten;
    requires org.jetbrains.annotations;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.material2;

    exports commppetterm.gui;
    opens commppetterm.gui to javafx.fxml;
    exports commppetterm.gui.page;
    opens commppetterm.gui.page to javafx.fxml;
    exports commppetterm.gui.exception;
    opens commppetterm.gui.exception to javafx.fxml;
    exports commppetterm.database;
    opens commppetterm.database to javafx.fxml;
}

