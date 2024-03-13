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
    private TextField link, user;

    @FXML
    private PasswordField password;

    /**
     * Creates a new settings controller
     */
    public Settings() throws URLNotFoundException, FxmlLoadException {
        this.link.setText(App.get().database().link());
        this.user.setText(App.get().database().user());
        this.password.setText(App.get().database().password());
    }

    @FXML
    private void save() throws SQLException {
        App.get().database().settings(this.link.getText(), this.user.getText(), this.password.getText());
        App.get().database().connect();

        if (App.get().database().connected()) {
            App.get().controller(new Calendar());
        }
    }

    @FXML
    private void cancel() throws URLNotFoundException, FxmlLoadException {
        App.get().controller(new Calendar());
    }
}
