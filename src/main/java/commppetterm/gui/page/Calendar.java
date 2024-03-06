package commppetterm.gui.page;

import org.jetbrains.annotations.NotNull;

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

    @FXML
    private Button edit, delete;

    @FXML
    private ToggleButton day, week, month;

    @FXML
    private Label label;

    @FXML
    private Pane pane;

    /**
     * Creates a new calendar
     */
    public Calendar() {
        calendarController = this;
    }

    @FXML
    private void create() throws ControllerLoadedException, URLNotFoundException, FxmlLoadException {
        Gui.get().swap(new Editor());
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
        if (this.day.isSelected()) {
            this.week.setSelected(false);
            this.month.setSelected(false);
            this.swap(new DayPage());
        } else {
            this.day.setSelected(true);
        }
    };

    @FXML
    private void week() throws ControllerLoadedException, URLNotFoundException, FxmlLoadException {
        if (this.week.isSelected()) {
            this.day.setSelected(false);
            this.month.setSelected(false);
            this.swap(new WeekPage());
        } else {
            this.week.setSelected(true);
        }
    };

    @FXML
    private void month() throws ControllerLoadedException, URLNotFoundException, FxmlLoadException {
        if (this.month.isSelected()) {
            this.day.setSelected(false);
            this.week.setSelected(false);
            this.swap(new MonthPage());
        } else {
            this.month.setSelected(true);
        }
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

        this.edit.setDisable(true);
        this.delete.setDisable(true);

        this.pageController = pageController;
        this.pane.getChildren().add(this.pageController.load());
        this.label.setText(this.pageController.label());
        Gui.get().stage().sizeToScene();
    }

    @Override
    protected void init() throws ControllerLoadedException, FxmlLoadException, URLNotFoundException {
        this.month.setSelected(true);
        this.swap(new MonthPage());
    }
}
