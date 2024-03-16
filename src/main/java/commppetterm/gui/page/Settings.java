package commppetterm.gui.page;

import commppetterm.gui.App;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.kordamp.ikonli.javafx.FontIcon;

import java.sql.SQLException;
import java.util.Optional;

/**
 * Settings controller
 */
public final class Settings extends Controller {
    @FXML
    private Label error;

    @FXML
    private FontIcon errorIcon;

    @FXML
    private TextField url, user, database, table;

    @FXML
    private PasswordField password;

    /**
     * Creates a new settings controller
     */
    public Settings() {
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

            App.get().provider(new Calendar());
        } catch (SQLException e) {
            Optional<ButtonType> res = App.get().alert(e, Alert.AlertType.ERROR, "Leave settings?", ButtonType.YES, ButtonType.NO);

            if (res.isPresent() && res.get().equals(ButtonType.YES)) {
                App.get().provider(new Calendar());
            }
        }
    }

    @FXML
    private void cancel() {
        try {
            App.get().database().test();
            App.get().provider(new Calendar());
        } catch (SQLException e) {
            Optional<ButtonType> res = App.get().alert(e, Alert.AlertType.ERROR, "Leave settings?", ButtonType.YES, ButtonType.NO);

            if (res.isPresent() && res.get().equals(ButtonType.YES)) {
                App.get().provider(new Calendar());
            }
        }
    }
}
