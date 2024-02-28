package commppetterm.gui.page;

import commppetterm.gui.exception.ControllerLoadedException;
import commppetterm.gui.exception.FxmlLoadException;
import commppetterm.gui.exception.URLNotFoundException;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Pane;

import java.time.LocalDate;

/**
 * Calendar controller class
 */
public final class CalendarPage extends Page {
    /**
     * The contained page's controller
     */
    private SubPage subPage;

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
    private void onPrev() { this.dateLab.setText(this.subPage.prev()); };

    @FXML
    private void onNext() { this.dateLab.setText(this.subPage.next()); };

    @FXML
    private void onDay() throws ControllerLoadedException, URLNotFoundException, FxmlLoadException {
        this.swap(new DaySubPage());
    };

    @FXML
    private void onWeek() throws ControllerLoadedException, URLNotFoundException, FxmlLoadException {
        this.swap(new WeekSubPage());
    };

    @FXML
    private void onMonth() throws ControllerLoadedException, URLNotFoundException, FxmlLoadException {
        this.swap(new MonthSubPage());
    };

    /**
     * Swaps to a different subpage
     * @param subPage The subpage to swap to
     */
    private void swap(SubPage subPage) throws ControllerLoadedException, URLNotFoundException, FxmlLoadException {
        if (this.subPage != null) {
            subPage.date = this.subPage.date;
            Parent parent = this.subPage.parent();

            if (parent != null) {
                this.contentPane.getChildren().remove(parent);
            }
        }

        this.subPage = subPage;
        this.contentPane.getChildren().add(this.subPage.load());
    }

    @Override
    protected void init() throws ControllerLoadedException, FxmlLoadException, URLNotFoundException {
        this.swap(new MonthSubPage());
    }
}
