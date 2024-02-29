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
public class Editor extends Controller {
    @FXML
    private Button saveBtn;

    @FXML
    private TextField titleFld, detailFld, startDateFld, startTimeFld, endDateFld, endTimeFld;

    @FXML
    private void onSave() {}

    @FXML
    private void onCancel() throws ControllerLoadedException, URLNotFoundException, FxmlLoadException {
        Gui.get().prepare(new Calendar());
    };
}
