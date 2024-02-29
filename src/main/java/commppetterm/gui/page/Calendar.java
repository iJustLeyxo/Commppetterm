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

/**
 * Calendar controller class
 */
public final class Calendar extends Controller {
    /**
     * This calendar controller
     */
    private static Calendar thiz;

    /**
     * Gets the calendar controller
     */
    public static Calendar get() { return thiz; }

    /**
     * The contained page's controller
     */
    private PageController subPage;

    @FXML
    private Button edit, delete, day, week, month;

    @FXML
    private Label date;

    @FXML
    private Pane pane;

    /**
     * Creates a new calendar
     */
    public Calendar() {
        thiz = this;
    }

    @FXML
    private void create() throws ControllerLoadedException, URLNotFoundException, FxmlLoadException {
        Gui.get().prepare(new Editor());
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
    private void prev() {
        this.subPage.prev();
        this.date.setText(this.subPage.label());
        Gui.get().stage().sizeToScene();
    };

    @FXML
    private void next() {
        this.subPage.next();
        this.date.setText(this.subPage.label());
        Gui.get().stage().sizeToScene();
    };

    @FXML
    private void day() throws ControllerLoadedException, URLNotFoundException, FxmlLoadException {
        this.swap(new DayPage(this.subPage.date));
    };

    @FXML
    private void week() throws ControllerLoadedException, URLNotFoundException, FxmlLoadException {
        this.swap(new WeekPage(this.subPage.date));
    };

    @FXML
    private void month() throws ControllerLoadedException, URLNotFoundException, FxmlLoadException {
        this.swap(new MonthPage(this.subPage.date));
    };

    /**
     * Swaps to a different page
     * @param pageController The page to swap to
     */
    public void swap(@NotNull PageController pageController) throws ControllerLoadedException, URLNotFoundException, FxmlLoadException {
        if (this.subPage != null) {
            Parent parent = this.subPage.parent();

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

        this.subPage = pageController;
        this.date.setText(this.subPage.label());
        this.pane.getChildren().add(this.subPage.load());
        Gui.get().stage().sizeToScene();
    }

    @Override
    protected void init() throws ControllerLoadedException, FxmlLoadException, URLNotFoundException {
        this.swap(new MonthPage());
    }
}
