package commppetterm.gui.page;

import commppetterm.gui.App;
import commppetterm.gui.exception.FxmlLoadException;
import commppetterm.gui.exception.URLNotFoundException;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.SQLException;

/**
 * Settings controller
 */
public final class Settings extends Controller {
    @FXML
    private TextField url, user, database, table;

    @FXML
    private PasswordField password;

    /**
     * Creates a new settings controller
     */
    public Settings() throws URLNotFoundException, FxmlLoadException {
        this.url.setText(App.get().database().url());
        this.user.setText(App.get().database().user());
        this.password.setText(App.get().database().password());
        this.database.setText(App.get().database().database());
        this.table.setText(App.get().database().table());
    }

    @FXML
    private void save() {
        try {
            App.get().database().update(
                    this.url.getText(),
                    this.database.getText(),
                    this.table.getText(),
                    this.user.getText(),
                    this.password.getText());

            App.get().controller(new Calendar());
        } catch (SQLException e) {
            App.get().LOGGER.warning(e.getMessage());
        }
    }

    @FXML
    private void cancel() throws URLNotFoundException, FxmlLoadException {
        try {
            App.get().database().test();
            App.get().controller(new Calendar());
        } catch (SQLException e) {
            App.get().controller(new Settings());
        }
    }
}
