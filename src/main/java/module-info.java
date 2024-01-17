module project {
    requires javafx.fxml;
    requires javafx.controls;
    
    opens project to javafx.fxml;
    exports project;
}