package commppetterm.gui;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Optional;
import java.util.logging.Logger;

import commppetterm.database.Database;
import commppetterm.database.Entry;
import commppetterm.database.exception.DatabaseException;
import commppetterm.database.exception.SettingsException;
import commppetterm.database.exception.SettingsLoadException;
import commppetterm.database.exception.SettingsSaveException;
import commppetterm.gui.exception.URLNotFoundException;
import commppetterm.gui.page.Provider;
import commppetterm.gui.page.Settings;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.jetbrains.annotations.NotNull;

import commppetterm.gui.page.Calendar;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.jetbrains.annotations.Nullable;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.material2.Material2AL;

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
    public final @NotNull String VERSION = "v0.1.1b";

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
    private @Nullable Provider provider;

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
    public void start(@NotNull Stage stage) {
        /* GUI setup */
        this.stage = stage;
        this.stage.setTitle(this.NAME + " " + this.VERSION);
        String iconFile = "icon.png";
        URL iconUrl = App.class.getResource(iconFile);

        if (iconUrl == null) {
            this.LOGGER.warning("Failed to load icon " + iconFile + " from " + App.class.getCanonicalName());
        } else {
            Image icon = new Image(iconUrl.toString());
            this.stage.getIcons().add(icon);
        }

        try {
            App.get().database().load();
            App.get().database().init();
            App.get().provider(new Calendar());
        } catch (DatabaseException e) {
            Optional<ButtonType> res = App.get().alert(e, Alert.AlertType.ERROR, "Go to settings?", ButtonType.YES, ButtonType.NO);

            if (res.isPresent() && res.get().equals(ButtonType.YES)) {
                App.get().provider(new Settings());
            } else {
                App.get().provider(new Calendar());
            }
        } catch (SettingsException e) {
            App.get().alert(e, Alert.AlertType.ERROR, null, ButtonType.OK);
            App.get().provider(new Calendar());
        }

        this.stage.show();
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
    public @Nullable Provider provider() { return this.provider; }

    /**
     * Swaps to a controller
     * @param provider The provider to swap to
     */
    public void provider(@NotNull Provider provider) {
        assert this.stage != null;
        this.stage.setScene(new Scene(provider.parent()));
        this.provider = provider;
    }

    /**
     * Creates an alert
     * @param type The alert type
     */
    public Optional<ButtonType> alert(@NotNull Exception e, @NotNull Alert.AlertType type, @Nullable String action, @NotNull ButtonType... buttons) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        URL url = App.get().getClass().getResource("style.css");

        FontIcon icon = new FontIcon(Material2AL.ERROR);

        alert.setTitle(type.toString());
        alert.setHeaderText(e.getClass().getCanonicalName() + ":");
        alert.getButtonTypes().setAll(buttons);
        alert.getDialogPane().setGraphic(icon);

        if (url == null) {
            throw new URLNotFoundException(App.get().getClass(), "style.css");
        } else {
            alert.getDialogPane().getScene().getStylesheets().add(url.toExternalForm());
        }

        if (action != null) {
            alert.setContentText(e.getMessage() + "\n\n" + action);
        } else {
            alert.setContentText(e.getMessage());
        }

        return alert.showAndWait();
    }}