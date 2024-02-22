package commppetterm.gui.page;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Pane;

/**
 * Calendar controller class
 */
public final class CalendarPage extends Controller {
    @FXML
    private Button editBtn, delBtn;

    @FXML
    private Label dateLab;

    @FXML
    private ToggleButton dayBtn, weekBtn, monthBtn;

    @FXML
    private Pane contentPane;

    @FXML
    private void onNew() {};

    @FXML
    private void onEdit() {}

    @FXML
    private void onDel() {};

    @FXML
    private void onPrev() {};

    @FXML
    private void onNext() {};

    @FXML
    private void onDay() {};

    @FXML
    private void onWeek() {};

    @FXML
    private void onMonth() {};
}
