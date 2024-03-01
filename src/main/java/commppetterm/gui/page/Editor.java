package commppetterm.gui.page;

import commppetterm.gui.Gui;
import commppetterm.gui.exception.ControllerLoadedException;
import commppetterm.gui.exception.FxmlLoadException;
import commppetterm.gui.exception.URLNotFoundException;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import org.jetbrains.annotations.NotNull;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.material2.Material2AL;
import org.kordamp.ikonli.material2.Material2MZ;

import java.time.LocalDate;

/**
 * Controller for entry editor
 */
public final class Editor extends Controller {
    @FXML
    private HBox startTimeHBox, endHBox, endTimeHBox;

    @FXML
    private Button saveButton, deleteButton;

    @FXML
    private ToggleButton endToggleButton, timeToggleButton, recurringToggleButton;

    @FXML
    private FontIcon startIcon, endIcon, refreshIcon;

    @FXML
    private TextField titleTextField, detailsTextField,
            startDayTextField, startMonthTextField, startYearTextField, startHourTextField, startMinuteTextField,
            endDayTextField, endMonthTextField, endYearTextField, endHourTextField, endMinuteTextField;

    /**
     * Creates a new editor controller
     * @param date The initial editor date
     */
    public Editor(@NotNull LocalDate date) {
        super(date);
    }

    @FXML
    private void saveButtonAction() {
        // TODO: Add save logic
    }

    @FXML
    private void deleteButtonAction() {
        // TODO: Add delete logic
    }

    @FXML
    private void cancelButtonAction() throws ControllerLoadedException, URLNotFoundException, FxmlLoadException {
        Gui.get().swap(new Calendar(this.date));
    };

    @FXML
    private void endToggleButtonAction() {
        if (this.endToggleButton.isSelected()) {
            this.startIcon.setIconCode(Material2AL.FIRST_PAGE);
        } else {
            this.startIcon.setIconCode(Material2MZ.TODAY);

            if (this.timeToggleButton.isSelected()) {
                this.timeToggleButton.setSelected(false);
                this.timeToggleButtonAction();
            }
        }

        this.enabled(this.endIcon, this.endToggleButton.isSelected());
        this.enabled(this.endHBox, this.endToggleButton.isSelected());
        Gui.get().stage().sizeToScene();
    }

    @FXML
    private void timeToggleButtonAction() {
        if (this.timeToggleButton.isSelected() && !this.endToggleButton.isSelected()) {
            this.endToggleButton.setSelected(true);
            this.endToggleButtonAction();
        }

        this.enabled(this.startTimeHBox, this.timeToggleButton.isSelected());
        this.enabled(this.endTimeHBox, this.timeToggleButton.isSelected());
        Gui.get().stage().sizeToScene();
    }

    @FXML
    private void recurringToggleButtonAction() {
        // TODO: [1] Change icons and add functionality for recurring event controls
    }

    @Override
    protected void init() {
        this.endToggleButtonAction();
        this.timeToggleButtonAction();
        this.recurringToggleButtonAction();
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
