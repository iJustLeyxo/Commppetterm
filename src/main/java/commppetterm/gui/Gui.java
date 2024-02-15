package commppetterm.gui;

import commppetterm.gui.page.Calender;
import commppetterm.util.GuiTools;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * User interface class
 */
public final class Gui extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        // GuiTools.prepare(stage, new Example());
        GuiTools.prepare(stage, new Calender());
    }

    public static void main(String[] args) {
        // Startet die JavaFX-Anwendung
        launch(args);
    }
}