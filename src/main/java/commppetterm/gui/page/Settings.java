package commppetterm.gui.page;

import commppetterm.database.Database;
import commppetterm.gui.Gui;
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
        Database.link = this.link.getText();
        Database.user = this.user.getText();
        Database.password = this.password.getText();
    }

    @FXML
    private void cancel() throws ControllerLoadedException, URLNotFoundException, FxmlLoadException {
        Gui.get().swap(new Calendar());
    }
}
