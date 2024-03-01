package commppetterm.gui.page;

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
    private HBox startTimeHBox, endHBox, endTimeHBox, recurringHBox;

    @FXML
    private Button saveButton, deleteButton, yearlyButton, monthlyButton, weeklyButton, dailyButton;

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
    private void endToggleButtonUpdate() {
        if (this.endToggleButton.isSelected()) {
            this.startIcon.setIconCode(Material2AL.FIRST_PAGE);
        } else {
            this.startIcon.setIconCode(Material2MZ.TODAY);

            if (this.timeToggleButton.isSelected()) {
                this.timeToggleButton.setSelected(false);
                this.timeToggleButtonUpdate();
            }
        }

        this.enabled(this.endIcon, this.endToggleButton.isSelected());
        this.enabled(this.endHBox, this.endToggleButton.isSelected());
        Gui.get().stage().sizeToScene();
    }

    @FXML
    private void timeToggleButtonUpdate() {
        if (this.timeToggleButton.isSelected() && !this.endToggleButton.isSelected()) {
            this.endToggleButton.setSelected(true);
            this.endToggleButtonUpdate();
        }

        this.enabled(this.startTimeHBox, this.timeToggleButton.isSelected());
        this.enabled(this.endTimeHBox, this.timeToggleButton.isSelected());
        Gui.get().stage().sizeToScene();
    }

    @FXML
    private void recurringToggleButtonUpdate() {
        // TODO: [1] Add functionality for recurring event controls
    }

    @FXML
    private void yearlyButtonAction() {}

    @FXML
    private void monthlyButtonAction() {}

    @FXML
    private void weeklyButtonAction() {}

    @FXML
    private void dailyButtonAction() {}

    @Override
    protected void init() {
        this.endToggleButtonUpdate();
        this.timeToggleButtonUpdate();
        this.recurringToggleButtonUpdate();
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
