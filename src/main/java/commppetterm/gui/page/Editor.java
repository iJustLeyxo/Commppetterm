package commppetterm.gui.page;

import commppetterm.gui.Gui;
import commppetterm.gui.exception.ControllerLoadedException;
import commppetterm.gui.exception.FxmlLoadException;
import commppetterm.gui.exception.URLNotFoundException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;

/**
 * Controller for entry editor
 */
public final class Editor extends Controller {
    @FXML
    private HBox startTimeHBox, endTimeHBox;

    @FXML
    private Button saveButton, deleteButton;

    @FXML
    private ToggleButton endToggleButton, timeToggleButton, recurringToggleButton;

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
        Gui.get().prepare(new Calendar());
    };

    @FXML
    private void endToggleButtonAction() {
        // TODO: Add end logic
    }

    @FXML
    private void timeToggleButtonAction() {
        // TODO: Add time logic
    }

    @FXML
    private void recurringToggleButtonAction() {
        // TODO: Add repeat logic
    }

    @Override
    protected void init() {
        this.endToggleButton.setSelected(false);
        this.timeToggleButton.setSelected(false);
        this.recurringToggleButton.setSelected(false);
    }

    // TODO: Make calendar memorize date

    // TODO: Increase font size
}
