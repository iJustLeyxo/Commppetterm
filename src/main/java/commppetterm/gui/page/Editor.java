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
import javafx.scene.layout.RowConstraints;

/**
 * Controller for entry editor
 */
public final class Editor extends Controller {
    @FXML
    private HBox startTime, endTime;

    @FXML
    private RowConstraints endRow;

    @FXML
    private Button save, delete;

    @FXML
    private ToggleButton end, time, repeat;

    @FXML
    private TextField title, detail, startDay, startMonth, startYear, startHour, startMinute, endDay, endMonth, endYear, endHour, endMinute;

    @FXML
    private void save() {
        // TODO: Add save logic
    }

    @FXML
    private void delete() {
        // TODO: Add delete logic
    }

    @FXML
    private void end() {
        // TODO: Add end logic
    }

    @FXML
    private void time() {
        // TODO: Add time logic
    }

    @FXML
    private void repeat() {
        // TODO: Add repeat logic
    }

    @FXML
    private void cancel() throws ControllerLoadedException, URLNotFoundException, FxmlLoadException {
        Gui.get().prepare(new Calendar());
    };

    @Override
    protected void init() {
        // TODO: Add init logic
    }

    // TODO: Make calendar memorize date

    // TODO: Increase font size
}
