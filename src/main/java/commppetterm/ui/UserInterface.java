package commppetterm.ui;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * User interface class
 */
public final class UserInterface extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Util.prepStage(stage, Util.loadScene(this.getClass(), "example"));
    }
    
    /**
     * Main method
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        launch();
    }
}