module commppetterm {
    requires javafx.fxml;
    requires javafx.controls;
    
    opens commppetterm to javafx.fxml;
    exports commppetterm;
}