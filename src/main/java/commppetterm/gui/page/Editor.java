package commppetterm.gui.page;

import commppetterm.gui.Gui;
import commppetterm.gui.exception.ControllerLoadedException;
import commppetterm.gui.exception.FxmlLoadException;
import commppetterm.gui.exception.URLNotFoundException;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;

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
    private Label endLabel;

    @FXML
    private TextField titleTextField, detailsTextField,
            startDayTextField, startMonthTextField, startYearTextField, startHourTextField, startMinuteTextField,
            endDayTextField, endMonthTextField, endYearTextField, endHourTextField, endMinuteTextField;

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
        Gui.get().swap(new Calendar());
    };

    @FXML
    private void endToggleButtonAction() {
        this.enabled(this.endLabel, this.endToggleButton.isSelected());
        this.enabled(this.endHBox, this.endToggleButton.isSelected());
        Gui.get().stage().sizeToScene();
    }

    @FXML
    private void timeToggleButtonAction() {
        this.enabled(this.startTimeHBox, this.timeToggleButton.isSelected());
        this.enabled(this.endTimeHBox, this.timeToggleButton.isSelected());
        Gui.get().stage().sizeToScene();
    }

    @FXML
    private void recurringToggleButtonAction() {
        // TODO: Add recurring logic and control elements
    }

    @Override
    protected void init() {
        this.endToggleButton.setSelected(false);
        this.timeToggleButton.setSelected(false);
        this.recurringToggleButton.setSelected(false);

        this.enabled(this.startTimeHBox, false);
        this.enabled(this.endTimeHBox, false);
        this.enabled(this.endLabel, false);
        this.enabled(this.endHBox, false);
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

    // TODO: Make calendar memorize date

    // TODO: Increase font size
}
