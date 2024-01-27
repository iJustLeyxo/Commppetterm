package commppetterm.ui.page;

import com.gluonhq.charm.glisten.control.ProgressBar;
import commppetterm.ui.Util;
import commppetterm.ui.page.Controller;
import javafx.application.Preloader;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

public final class Loader extends Preloader implements Controller {
    @FXML
    private ProgressBar bar;
    
    @Override
    public void start(Stage stage) throws Exception {
        Util.prepare(stage, this);
    }
    
    /**
     * Main method
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        launch();
    }
    
    @Override
    public @NotNull String path() {
        return "loader.fxml";
    }
}