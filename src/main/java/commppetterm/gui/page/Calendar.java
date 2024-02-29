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
    private Button editButton, deleteButton, dayButton, weekButton, monthButton;

    @FXML
    private Label dateLabel;

    @FXML
    private Pane contentPane;

    /**
     * Creates a new calendar
     */
    public Calendar() {
        calendarController = this;
    }

    @FXML
    private void createButtonAction() throws ControllerLoadedException, URLNotFoundException, FxmlLoadException {
        Gui.get().prepare(new Editor());
    };

    @FXML
    private void editButtonAction() {
        // TODO: Add editing
    }

    @FXML
    private void deleteButtonAction() {
        // TODO: Add deleting
    };

    @FXML
    private void previousButtonAction() {
        this.pageController.prev();
        this.dateLabel.setText(this.pageController.label());
        Gui.get().stage().sizeToScene();
    };

    @FXML
    private void nextButtonAction() {
        this.pageController.next();
        this.dateLabel.setText(this.pageController.label());
        Gui.get().stage().sizeToScene();
    };

    @FXML
    private void dayButtonAction() throws ControllerLoadedException, URLNotFoundException, FxmlLoadException {
        this.swap(new DayPage(this.pageController.date));
    };

    @FXML
    private void weekButtonAction() throws ControllerLoadedException, URLNotFoundException, FxmlLoadException {
        this.swap(new WeekPage(this.pageController.date));
    };

    @FXML
    private void monthButtonAction() throws ControllerLoadedException, URLNotFoundException, FxmlLoadException {
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
                this.contentPane.getChildren().remove(parent);
            }
        }

        this.dayButton.setDisable(false);
        this.weekButton.setDisable(false);
        this.monthButton.setDisable(false);
        this.editButton.setDisable(false);
        this.deleteButton.setDisable(false);

        if (pageController instanceof DayPage) {
            this.dayButton.setDisable(true);
        } else if (pageController instanceof WeekPage) {
            this.weekButton.setDisable(true);
        } else {
            this.monthButton.setDisable(true);
            this.editButton.setDisable(true);
            this.deleteButton.setDisable(true);
        }

        this.pageController = pageController;
        this.dateLabel.setText(this.pageController.label());
        this.contentPane.getChildren().add(this.pageController.load());
        Gui.get().stage().sizeToScene();
    }

    @Override
    protected void init() throws ControllerLoadedException, FxmlLoadException, URLNotFoundException {
        this.swap(new MonthPage());
    }
}
