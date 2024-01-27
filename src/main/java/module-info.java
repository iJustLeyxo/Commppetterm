module commppetterm {
    requires javafx.fxml;
    requires javafx.controls;
    requires com.gluonhq.charm.glisten;
    requires org.jetbrains.annotations;
    
    opens commppetterm to javafx.fxml;
    exports commppetterm;
    exports commppetterm.ui;
    opens commppetterm.ui to javafx.fxml;
    exports commppetterm.ui.page;
    opens commppetterm.ui.page to javafx.fxml;
}