package commppetterm.gui;

import commppetterm.gui.page.Example;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * User interface class
 */
public final class Gui extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Util.prepare(stage, new Example());
    }
}