package commppetterm.gui.page;

import commppetterm.gui.Gui;
import commppetterm.gui.exception.ControllerLoadedException;
import commppetterm.gui.exception.FxmlLoadException;
import commppetterm.gui.exception.URLNotFoundException;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Pane;
import org.jetbrains.annotations.NotNull;

/**
 * Calendar controller class
 */
public final class CalendarPage extends Controller {
    /**
     * This calendar page controller
     */
    private static CalendarPage thiz;

    /**
     * Gets the calendar page controller
     */
    public static CalendarPage get() { return thiz; }

    /**
     * The contained page's controller
     */
    private SubPageController subPage;

    @FXML
    private Button editBtn, delBtn, dayBtn, weekBtn, monthBtn;

    @FXML
    private Label dateLab;

    @FXML
    private Pane contentPane;

    /**
     * Creates a new calendar page
     */
    public CalendarPage() {
        thiz = this;
    }

    //TODO: Add editing, adding and deleting of entries

    @FXML
    private void onNew() {};

    @FXML
    private void onEdit() {}

    @FXML
    private void onDel() {};

    @FXML
    private void onPrev() {
        this.subPage.prev();
        this.dateLab.setText(this.subPage.label());
        Gui.get().stage().sizeToScene();
    };

    @FXML
    private void onNext() {
        this.subPage.next();
        this.dateLab.setText(this.subPage.label());
        Gui.get().stage().sizeToScene();
    };

    @FXML
    private void onDay() throws ControllerLoadedException, URLNotFoundException, FxmlLoadException {
        this.swap(new DaySubPage(this.subPage.date));
    };

    @FXML
    private void onWeek() throws ControllerLoadedException, URLNotFoundException, FxmlLoadException {
        this.swap(new WeekSubPage(this.subPage.date));
    };

    @FXML
    private void onMonth() throws ControllerLoadedException, URLNotFoundException, FxmlLoadException {
        this.swap(new MonthSubPage(this.subPage.date));
    };

    /**
     * Swaps to a different subpage
     * @param subPageController The subpage to swap to
     */
    public void swap(@NotNull SubPageController subPageController) throws ControllerLoadedException, URLNotFoundException, FxmlLoadException {
        if (this.subPage != null) {
            Parent parent = this.subPage.parent();

            if (parent != null) {
                this.contentPane.getChildren().remove(parent);
            }
        }

        this.dayBtn.setDisable(false);
        this.weekBtn.setDisable(false);
        this.monthBtn.setDisable(false);

        if (subPageController instanceof DaySubPage) {
            this.dayBtn.setDisable(true);
        } else if (subPageController instanceof WeekSubPage) {
            this.weekBtn.setDisable(true);
        } else {
            this.monthBtn.setDisable(true);
        }

        this.subPage = subPageController;
        this.dateLab.setText(this.subPage.label());
        this.contentPane.getChildren().add(this.subPage.load());
        Gui.get().stage().sizeToScene();
    }

    @Override
    protected void init() throws ControllerLoadedException, FxmlLoadException, URLNotFoundException {
        this.swap(new MonthSubPage());
    }
}
