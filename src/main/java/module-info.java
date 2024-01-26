module commppetterm {
    requires javafx.fxml;
    requires javafx.controls;
    
    opens commppetterm to javafx.fxml;
    exports commppetterm;
    exports commppetterm.ui;
    opens commppetterm.ui to javafx.fxml;
    exports commppetterm.ui.ctrl;
    opens commppetterm.ui.ctrl to javafx.fxml;
}