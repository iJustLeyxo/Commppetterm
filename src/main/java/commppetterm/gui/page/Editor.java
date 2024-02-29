package commppetterm.gui.page;

import commppetterm.gui.Gui;
import commppetterm.gui.exception.ControllerLoadedException;
import commppetterm.gui.exception.FxmlLoadException;
import commppetterm.gui.exception.URLNotFoundException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;

/**
 * Controller for entry editor
 */
public final class Editor extends Controller {
    @FXML
    private HBox startTime, endTime;

    @FXML
    private RowConstraints end;

    @FXML
    private Button save, delete;

    @FXML
    private TextField title, detail, startDay, startMonth, startYear, startHour, startMinute, endDay, endMonth, endYear, endHour, endMinute;

    @FXML
    private void save() {}

    @FXML
    private void delete() {}

    @FXML
    private void cancel() throws ControllerLoadedException, URLNotFoundException, FxmlLoadException {
        Gui.get().prepare(new Calendar());
    };

    // TODO: Add options for whole day events, recurring events and single day events

    // TODO: Make calendar memorize date
}
