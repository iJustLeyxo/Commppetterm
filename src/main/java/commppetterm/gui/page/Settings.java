package commppetterm.gui.page;

import commppetterm.database.Database;
import commppetterm.database.exception.ConnectionException;
import commppetterm.database.exception.DatabaseException;
import commppetterm.database.exception.SettingsSaveException;
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
        this.url.setText(App.get().database().settings().url());
        this.user.setText(App.get().database().settings().user());
        this.password.setText(App.get().database().settings().password());
        this.database.setText(App.get().database().settings().database());
        this.table.setText(App.get().database().settings().table());
    }

    @FXML
    private void save() {
        App.get().database().update( new Database.Settings(
                this.url.getText(),
                this.database.getText(),
                this.table.getText(),
                this.user.getText(),
                this.password.getText())
        );

        boolean leave = true;

        try {
            App.get().database().settings().save();
        } catch (SettingsSaveException e){
                Optional<ButtonType> res = App.get().alert(e, Alert.AlertType.ERROR, "Continue anyway?", ButtonType.YES, ButtonType.NO);

            if (res.isPresent() && res.get().equals(ButtonType.NO)) {
                leave = false;
            }
        }

        try {
            App.get().database().init();
        } catch (DatabaseException e) {
            Optional<ButtonType> res = App.get().alert(e, Alert.AlertType.ERROR, "Leave settings?", ButtonType.YES, ButtonType.NO);

            if (res.isPresent() && res.get().equals(ButtonType.NO)) {
                leave = false;
            }
        }

        if (leave) {
            App.get().provider(new Calendar());
        }
    }

    @FXML
    private void cancel() {
        try {
            App.get().database().test();
            App.get().provider(new Calendar());
        } catch (ConnectionException e) {
            Optional<ButtonType> res = App.get().alert(e, Alert.AlertType.ERROR, "Leave settings?", ButtonType.YES, ButtonType.NO);

            if (res.isPresent() && res.get().equals(ButtonType.YES)) {
                App.get().provider(new Calendar());
            }
        }
    }
}
