package commppetterm.gui;

import java.net.URL;
import java.time.LocalDate;
import java.util.Locale;
import java.util.logging.Logger;

import commppetterm.database.Entry;
import org.jetbrains.annotations.NotNull;

import commppetterm.gui.exception.ControllerLoadedException;
import commppetterm.gui.exception.FxmlLoadException;
import commppetterm.gui.exception.URLNotFoundException;
import commppetterm.gui.page.Calendar;
import commppetterm.gui.page.Controller;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.jetbrains.annotations.Nullable;

/**
 * Main class
 */
public final class App extends Application {
    /**
     * The gui handler class
     */
    private static App app;

    /**
     * Returns the gui handler class
     */
    public static App get() { return app; }

    /**
     * Main method
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /* == == == == == */

    /**
     * The application stage
     */
    private Stage stage;

    /**
     * Gui date
     */
    private @NotNull LocalDate date = LocalDate.now();

    /**
     * Gui entry reference
     */
    private @Nullable Entry entry = null;

    /**
     * App name
     */
    public final String name = "Commppetterm";

    /**
     * Application version
     */
    public final String ver = "0.0.2a";

    /**
     * Application locale
     */
    public final Locale locale = Locale.GERMANY;

    /**
     * Application logger
     */
    public final Logger logger = Logger.getLogger(App.class.getName());

    /**
     * Initialize the stage
     * @param stage Application stage
     */
    @Override
    public void start(@NotNull Stage stage) throws ControllerLoadedException, URLNotFoundException, FxmlLoadException {
        this.stage = stage;
        this.stage.setTitle(this.name);
        String iconFile = "icon.png";
        URL iconUrl = App.class.getResource(iconFile);

        if (iconUrl == null) {
            this.logger.warning("Failed to load icon " + iconFile + " from " + App.class.getCanonicalName());
        } else {
            Image icon = new Image(iconUrl.toString());
            this.stage.getIcons().add(icon);
        }

        this.swap(new Calendar());
        this.stage.show();
    }

    /**
     * Creates a gui application
     */
    public App() {
        app = this;
    }

    /**
     * @return the application stage
     */
    public Stage stage() { return this.stage; }

    /**
     * @return the selected date
     */
    public @NotNull LocalDate date() { return this.date; }

    /**
     * @param date The new date to set
     */
    public void date(@NotNull LocalDate date) { this.date = date; }

    /**
     * @return the selected entry
     */
    public @Nullable Entry entry() { return this.entry; }

    /**
     * @param entry The new entry to set
     */
    public void entry(@Nullable Entry entry) { this.entry = entry; }

    /**
     * Swaps to a controller
     * @param controller The controller to swap to
     */
    public void swap(@NotNull Controller controller) throws ControllerLoadedException, URLNotFoundException, FxmlLoadException {
        this.stage.setScene(new Scene(controller.load()));
    }
}