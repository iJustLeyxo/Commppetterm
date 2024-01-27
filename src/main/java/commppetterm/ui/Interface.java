package commppetterm.ui;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * User interface class
 */
public final class Interface extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Util.prepare(stage, "preLoader");
    }
}