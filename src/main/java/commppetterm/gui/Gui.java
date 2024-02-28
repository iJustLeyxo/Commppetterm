package commppetterm.gui;

import commppetterm.gui.exception.ControllerLoadedException;
import commppetterm.gui.exception.FxmlLoadException;
import commppetterm.gui.exception.URLNotFoundException;
import commppetterm.gui.page.CalendarPage;
import org.jetbrains.annotations.NotNull;
import commppetterm.App;
import commppetterm.gui.page.Controller;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.net.URL;

/**
 * Main class
 */
public final class Gui extends Application {
    /**
     * The gui handler class
     */
    private static Gui gui;

    /**
     * Main method
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Returns the gui handler class
     */
    public static Gui get() { return gui; }

    /**
     * The application stage
     */
    private Stage stage;

    /**
     * Creates a gui application
     */
    public Gui() {
        gui = this;
    }

    /**
     * JavaFX custom start method
     * @param stage Application stage
     */
    @Override
    public void start(Stage stage) throws ControllerLoadedException, URLNotFoundException, FxmlLoadException {
        this.stage = stage;
        this.prepare(new CalendarPage());
        this.stage.show();
    }

    /**
     * Returns the application stage
     */
    public Stage stage() { return this.stage; }

    /**
     * Prepares a stage
     * @param controller The class to load the scene from
     */
    public void prepare(@NotNull Controller controller) throws ControllerLoadedException, URLNotFoundException, FxmlLoadException {
        this.stage.setScene(new Scene(controller.load()));
        this.stage.setTitle(App.name);
        String iconFile = "icon.png";
        URL iconUrl = Gui.class.getResource(iconFile);
        if (iconUrl == null) {
            App.logger.warning("Failed to load icon " + iconFile + " from " + Gui.class.getCanonicalName());
        } else {
            Image icon = new Image(iconUrl.toString());
            this.stage.getIcons().add(icon);
        }
        this.stage.show();
    }
}