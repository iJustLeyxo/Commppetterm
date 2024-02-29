package commppetterm.gui.page;

import commppetterm.gui.Gui;
import commppetterm.gui.exception.ControllerLoadedException;
import commppetterm.gui.exception.FxmlLoadException;
import commppetterm.gui.exception.URLNotFoundException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * Controller for entry editor
 */
public final class Editor extends Controller {
    @FXML
    private Button save;

    @FXML
    private TextField title, detail, startDay, startMonth, startYear, startHour, startMinute, endDay, endMonth, endYear, endHour, endMinute;

    @FXML
    private void save() {}

    @FXML
    private void cancel() throws ControllerLoadedException, URLNotFoundException, FxmlLoadException {
        Gui.get().prepare(new Calendar());
    };
}
