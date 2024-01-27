package commppetterm.ui;

import com.gluonhq.charm.glisten.control.ProgressBar;
import javafx.application.Preloader;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public final class Loader extends Preloader {
    @FXML
    private ProgressBar bar;
    
    @Override
    public void start(Stage stage) throws Exception {
        Util.prepare(stage, "loader");
    }
    
    /**
     * Main method
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        launch();
    }
}