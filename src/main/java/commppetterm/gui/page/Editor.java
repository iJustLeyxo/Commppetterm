package commppetterm.gui.page;

import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.material2.Material2AL;
import org.kordamp.ikonli.material2.Material2MZ;

import commppetterm.gui.Gui;
import commppetterm.gui.exception.ControllerLoadedException;
import commppetterm.gui.exception.FxmlLoadException;
import commppetterm.gui.exception.URLNotFoundException;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;


/**
 * Controller for entry editor
 */
public final class Editor extends Controller {
    @FXML
    private HBox startTime, endBox, endTime, recurringBox;

    @FXML
    private Button save, delete;

    @FXML
    private ToggleButton end, time, recurring, yearly, monthly, weekly, daily;

    @FXML
    private FontIcon startIcon, endIcon, recurringIcon;

    @FXML
    private TextField title, info,
            startDay, startMonth, startYear, startHour, startMinute,
            endDay, endMonth, endYear, endHour, endMinute;

    @FXML
    private void save() {
        // TODO: Add save logic
    }

    @FXML
    private void delete() {
        // TODO: Add delete logic
    }

    @FXML
    private void cancel() throws ControllerLoadedException, URLNotFoundException, FxmlLoadException {
        Gui.get().swap(new Calendar());
    };

    @FXML
    private void end() {
        if (this.end.isSelected()) {
            this.startIcon.setIconCode(Material2AL.FIRST_PAGE);
        } else {
            this.startIcon.setIconCode(Material2MZ.TODAY);

            if (this.time.isSelected()) {
                this.time.setSelected(false);
                this.time();
            }
        }

        this.enabled(this.endIcon, this.end.isSelected());
        this.enabled(this.endBox, this.end.isSelected());
        Gui.get().stage().sizeToScene();
    }

    @FXML
    private void time() {
        if (this.time.isSelected() && !this.end.isSelected()) {
            this.end.setSelected(true);
            this.end();
        }

        this.enabled(this.startTime, this.time.isSelected());
        this.enabled(this.endTime, this.time.isSelected());
        Gui.get().stage().sizeToScene();
    }

    @FXML
    private void recurring() {
        this.enabled(recurringIcon, this.recurring.isSelected());
        this.enabled(recurringBox, this.recurring.isSelected());
        Gui.get().stage().sizeToScene();
    }

    @FXML
    private void yearly() {
        if (this.yearly.isSelected()) {
            this.daily.setSelected(false);
            this.weekly.setSelected(false);
            this.monthly.setSelected(false);
        } else {
            this.yearly.setSelected(true);
        }
    }

    @FXML
    private void monthly() {
        if (this.monthly.isSelected()) {
            this.daily.setSelected(false);
            this.weekly.setSelected(false);
            this.yearly.setSelected(false);
        } else {
            this.monthly.setSelected(true);
        }
    }

    @FXML
    private void weekly() {
        if (this.weekly.isSelected()) {
            this.daily.setSelected(false);
            this.monthly.setSelected(false);
            this.yearly.setSelected(false);
        } else {
            this.weekly.setSelected(true);
        }
    }

    @FXML
    private void daily() {
        if (this.daily.isSelected()) {
            this.weekly.setSelected(false);
            this.monthly.setSelected(false);
            this.yearly.setSelected(false);
        } else {
            this.daily.setSelected(true);
        }
    }

    @Override
    protected void init() {
        this.end();
        this.time();
        this.recurring();
        this.yearly.setSelected(true);
    }

    /**
     * Enables or disables a node, can make the node invisible and exclude the node from rendering
     * @param node The node to edit
     * @param value The value to set
     */
    private void enabled(Node node, boolean value) {
        node.setVisible(value);
        node.setManaged(value);
    }
}
