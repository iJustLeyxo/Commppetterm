package commppetterm.gui;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Locale;
import java.util.logging.Logger;

import commppetterm.database.Database;
import commppetterm.database.Entry;
import commppetterm.gui.page.Settings;
import org.jetbrains.annotations.NotNull;

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
    private static @Nullable App app;

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

    /**
     * App name
     */
    public final @NotNull String NAME = "Commppetterm";

    /**
     * Application version
     */
    public final @NotNull String VERSION = "0.0.2a";

    /**
     * Application locale
     */
    public final @NotNull Locale LOCALE = Locale.GERMANY;

    /**
     * Application logger
     */
    public final @NotNull Logger LOGGER = Logger.getLogger(App.class.getName());

    /**
     * The application stage
     */
    private @Nullable Stage stage;

    /**
     * Database interface
     */
    private final @Nullable Database database;

    /**
     * Current controller
     */
    private @Nullable Controller controller;

    /**
     * Gui date
     */
    private @NotNull LocalDate date = LocalDate.now();

    /**
     * Gui entry reference
     */
    private @Nullable Entry entry = null;

    /**
     * Initializes a new app
     */
    public App() {
        app = this;
        this.database = new Database();
    }

    /**
     * Initialize the stage
     * @param stage Application stage
     */
    @Override
    public void start(@NotNull Stage stage) throws URLNotFoundException, FxmlLoadException {
        /* GUI setup */
        this.stage = stage;
        this.stage.setTitle(this.NAME);
        String iconFile = "icon.png";
        URL iconUrl = App.class.getResource(iconFile);

        if (iconUrl == null) {
            this.LOGGER.warning("Failed to load icon " + iconFile + " from " + App.class.getCanonicalName());
        } else {
            Image icon = new Image(iconUrl.toString());
            this.stage.getIcons().add(icon);
        }

        assert this.database != null;
        if (this.database.connected()) {
            this.controller(new Calendar());
        } else {
            this.controller(new Settings());
        }

        this.stage.show();
    }

    /**
     * Shutdown application logic
     */
    @Override
    public void stop() throws SQLException {
        assert this.database != null;
        this.database.disconnect();
    }

    /**
     * @return the application stage
     */
    public Stage stage() { return this.stage; }

    /**
     * @return the application database interface
     */
    public Database database() { return this.database; }

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
     * @return the current controller
     */
    public @Nullable Controller controller() { return this.controller; }

    /**
     * Swaps to a controller
     * @param controller The controller to swap to
     */
    public void controller(@NotNull Controller controller) throws URLNotFoundException, FxmlLoadException {
        assert this.stage != null;
        this.stage.setScene(new Scene(controller.parent()));
        this.controller = controller;
    }
}