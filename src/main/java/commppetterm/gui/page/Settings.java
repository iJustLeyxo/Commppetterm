package commppetterm.gui.page;

import commppetterm.gui.App;
import commppetterm.gui.exception.ControllerLoadedException;
import commppetterm.gui.exception.FxmlLoadException;
import commppetterm.gui.exception.URLNotFoundException;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * Settings controller
 */
public final class Settings extends Controller {
    @FXML
    private TextField link, user;

    @FXML
    private PasswordField password;

    @FXML
    private void save() {
        App.get().database().settings(this.link.getText(), this.user.getText(), this.password.getText());
    }

    @FXML
    private void cancel() throws ControllerLoadedException, URLNotFoundException, FxmlLoadException {
        App.get().controller(new Calendar());
    }
}
