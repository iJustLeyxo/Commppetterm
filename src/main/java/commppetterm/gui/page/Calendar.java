package commppetterm.gui.page;

import commppetterm.gui.Gui;
import commppetterm.gui.exception.ControllerLoadedException;
import commppetterm.gui.exception.FxmlLoadException;
import commppetterm.gui.exception.URLNotFoundException;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

/**
 * Calendar controller class
 */
public final class Calendar extends Controller {
    /**
     * This calendar controller
     */
    private static Calendar calendarController;

    /**
     * Gets the calendar controller
     */
    public static Calendar get() { return calendarController; }

    /**
     * The contained page's controller
     */
    private PageController pageController;

    // TODO: Make toggleable buttons real toggle-buttons

    @FXML
    private Button edit, delete, day, week, month;

    @FXML
    private Label label;

    @FXML
    private Pane pane;

    /**
     * Creates a new calendar
     * @param date Calendar initial date
     */
    public Calendar(@NotNull LocalDate date) {
        super(date);
        calendarController = this;
    }

    @FXML
    private void create() throws ControllerLoadedException, URLNotFoundException, FxmlLoadException {
        Gui.get().swap(new Editor(this.pageController.date));
    };

    @FXML
    private void edit() {
        // TODO: Add editing
    }

    @FXML
    private void delete() {
        // TODO: Add deleting
    };

    @FXML
    private void previous() {
        this.pageController.prev();
        this.label.setText(this.pageController.label());
        Gui.get().stage().sizeToScene();
    };

    @FXML
    private void next() {
        this.pageController.next();
        this.label.setText(this.pageController.label());
        Gui.get().stage().sizeToScene();
    };

    @FXML
    private void day() throws ControllerLoadedException, URLNotFoundException, FxmlLoadException {
        this.swap(new DayPage(this.pageController.date));
    };

    @FXML
    private void week() throws ControllerLoadedException, URLNotFoundException, FxmlLoadException {
        this.swap(new WeekPage(this.pageController.date));
    };

    @FXML
    private void month() throws ControllerLoadedException, URLNotFoundException, FxmlLoadException {
        this.swap(new MonthPage(this.pageController.date));
    };

    /**
     * Swaps to a different page
     * @param pageController The page to swap to
     */
    public void swap(@NotNull PageController pageController) throws ControllerLoadedException, URLNotFoundException, FxmlLoadException {
        if (this.pageController != null) {
            Parent parent = this.pageController.parent();

            if (parent != null) {
                this.pane.getChildren().remove(parent);
            }
        }

        this.day.setDisable(false);
        this.week.setDisable(false);
        this.month.setDisable(false);
        this.edit.setDisable(false);
        this.delete.setDisable(false);

        if (pageController instanceof DayPage) {
            this.day.setDisable(true);
        } else if (pageController instanceof WeekPage) {
            this.week.setDisable(true);
        } else {
            this.month.setDisable(true);
            this.edit.setDisable(true);
            this.delete.setDisable(true);
        }

        this.pageController = pageController;
        this.pane.getChildren().add(this.pageController.load());
        this.label.setText(this.pageController.label());
        Gui.get().stage().sizeToScene();
    }

    @Override
    protected void init() throws ControllerLoadedException, FxmlLoadException, URLNotFoundException {
        this.swap(new MonthPage(this.date));
    }
}
