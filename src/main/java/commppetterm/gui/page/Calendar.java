package commppetterm.gui.page;

import commppetterm.App;
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

import java.util.logging.Logger;

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
        this.reload();
    };

    @FXML
    private void next() {
        this.pageController.next();
        this.reload();
    };

    @FXML
    private void day() throws ControllerLoadedException, URLNotFoundException, FxmlLoadException {
        this.swap(new DayPage());
    };

    @FXML
    private void week() throws ControllerLoadedException, URLNotFoundException, FxmlLoadException {
        this.swap(new WeekPage());
    };

    @FXML
    private void month() throws ControllerLoadedException, URLNotFoundException, FxmlLoadException {
        this.swap(new MonthPage());
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
        
        this.day.setSelected(false);
        this.week.setSelected(false);
        this.month.setSelected(false);

        switch (pageController) {
            case DayPage page -> this.day.setSelected(true);
            case WeekPage page -> this.week.setSelected(true);
            case MonthPage page -> this.month.setSelected(true);
            default -> App.logger.warning("Unexpected page controller: " + pageController.getClass().getName());
        }

        this.edit.setDisable(true);
        this.delete.setDisable(true);

        this.pageController = pageController;
        this.pane.getChildren().add(this.pageController.load());
        this.reload();
    }

    /**
     * Reloads the calendar contents
     */
    private void reload() {
        this.label.setText(this.pageController.label());
        Gui.get().stage().sizeToScene();
    }

    @Override
    protected void init() throws ControllerLoadedException, FxmlLoadException, URLNotFoundException {
        this.month.setSelected(true);
        this.swap(new MonthPage());
    }
}
