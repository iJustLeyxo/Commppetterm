package commppetterm.gui.page;

import commppetterm.gui.App;
import commppetterm.gui.exception.FxmlLoadException;
import commppetterm.gui.exception.URLNotFoundException;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;

/**
 * Settings controller
 */
public final class Settings extends Controller {
    @FXML
    private Label error;

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

    /**
     * Creates a new settings controller
     */
    public Settings(@NotNull Throwable error) throws URLNotFoundException, FxmlLoadException {
        this.url.setText(App.get().database().url());
        this.user.setText(App.get().database().user());
        this.password.setText(App.get().database().password());
        this.database.setText(App.get().database().database());
        this.table.setText(App.get().database().table());
        this.error(error);
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
            this.error(e);
        }
    }

    @FXML
    private void cancel() throws URLNotFoundException, FxmlLoadException {
        try {
            App.get().database().test();
            App.get().controller(new Calendar());
        } catch (SQLException e) {
            this.error(e);
        }
    }

    /**
     * Enables or disables a node, can make the node invisible and exclude the node from rendering
     * @param error The error to show
     */
    private void error(@NotNull Throwable error) {
        this.error.setText(error.getMessage());
        this.error.setVisible(true);
        this.error.setManaged(true);
    }
}
