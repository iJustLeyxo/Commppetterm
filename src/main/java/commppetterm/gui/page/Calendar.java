package commppetterm.gui.page;

import org.jetbrains.annotations.NotNull;

import commppetterm.gui.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Pane;

/**
 * Calendar controller class
 */
public final class Calendar extends Controller {
    /**
     * The contained page's controller
     */
    private @NotNull PageController pageController;

    @FXML
    private Button edit;

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
        this.month.setSelected(true);
        this.pageController = new MonthPage();

        this.day.setSelected(false);
        this.week.setSelected(false);
        this.month.setSelected(true);
        this.edit.setDisable(true);

        this.pane.getChildren().add(this.pageController.parent());
        this.reload();
    }

    @FXML
    private void create() {
        App.get().entry(null);
        App.get().provider(new Editor());
    }

    @FXML
    private void edit() {
        if (App.get().entry() != null) {
            App.get().provider(new Editor());
        } else {
            this.edit.setDisable(true);
        }
    }

    @FXML
    private void settings() {
        App.get().provider(new Settings());
    }

    @FXML
    private void previous() {
        App.get().date(this.pageController.prev(App.get().date()));
        this.reload();
    }

    @FXML
    private void next() {
        App.get().date(this.pageController.next(App.get().date()));
        this.reload();
    }

    @FXML
    private void day() {
        this.swap(new DayPage());
    }

    @FXML
    private void week() {
        this.swap(new WeekPage());
    }

    @FXML
    private void month() {
        this.swap(new MonthPage());
    }

    /**
     * Swaps to a different page
     * @param pageController The page to swap to
     */
    public void swap(@NotNull PageController pageController) {
        this.pane.getChildren().remove(this.pageController.parent());

        this.day.setSelected(false);
        this.week.setSelected(false);
        this.month.setSelected(false);

        switch (pageController) {
            case DayPage page -> this.day.setSelected(true);
            case WeekPage page -> this.week.setSelected(true);
            case MonthPage page -> this.month.setSelected(true);
            default -> App.get().LOGGER.warning("Unexpected page controller: " + pageController.getClass().getName());
        }

        this.edit.setDisable(true);

        this.pageController = pageController;
        this.pane.getChildren().add(this.pageController.parent());
        this.reload();
    }

    /**
     * Reloads the calendar contents
     */
    protected void reload() {
        this.pageController.reload();
        this.label.setText(this.pageController.label());
        App.get().stage().sizeToScene();
    }

    /**
     * Enable editing
     */
    public void enableEdit() {
        this.edit.setDisable(false);
    }
}
