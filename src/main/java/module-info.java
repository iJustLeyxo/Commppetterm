module commppetterm {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.gluonhq.charm.glisten;
    requires org.jetbrains.annotations;

    opens commppetterm to javafx.fxml;
    opens commppetterm.gui to javafx.fxml;
    opens commppetterm.gui.page to javafx.fxml;

    exports commppetterm;
    exports commppetterm.gui;
    exports commppetterm.gui.page;
}

