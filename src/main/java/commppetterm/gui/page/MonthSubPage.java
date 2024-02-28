package commppetterm.gui.page;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.LinkedList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import org.jetbrains.annotations.NotNull;

/**
 * Subpage for showing months
 */
public final class MonthSubPage extends PageController {
    @FXML
    private GridPane grid;

    /**
     * List of all visible buttons in the grid
     */
    private LinkedList<Parent> elements;

    /**
     * Creates a new month subpage
     */
    public MonthSubPage() {
        super(DateTimeFormatter.ofPattern("MMM yyyy"));
    }

    @Override
    protected void init() {
        this.elements = new LinkedList<>();
        this.date = LocalDate.now();
        this.reload();
    }

    /**
     * Reloads the contents
     */
    private void reload() {
        /* Clear grid pane */
        this.grid.getChildren().removeAll(elements);
        this.elements = new LinkedList<>();

        /* Generate days */
        LocalDate firstDayOfMonth = LocalDate.of(this.date.getYear(), this.date.getMonth(), 1);
        Parent element = null;
        int firstDayOfWeek = firstDayOfMonth.getDayOfWeek().getValue();
        int daysInMonth = this.date.lengthOfMonth();

        for (int dayOfMonth = 1; dayOfMonth <= daysInMonth; dayOfMonth++) {
            int dayOfWeek = (dayOfMonth + firstDayOfWeek - 2) % 7 + 1;
            int weekOfMonth = (dayOfMonth + firstDayOfWeek - 2) / 7 + 1;

            element = new DayCellController(LocalDate.of(firstDayOfMonth.getYear(), firstDayOfMonth.getMonth(), dayOfMonth)).load();
            this.elements.add(element);
            this.grid.add(element, dayOfWeek, weekOfMonth);
        }

        /* Generate weeks */
        int firstWeekOfMonth = firstDayOfMonth.get(WeekFields.ISO.weekOfYear());
        int weeksInMonth = (firstDayOfWeek + daysInMonth + 5) / 7;

        for (int weekOfMonth = 1; weekOfMonth <= weeksInMonth; weekOfMonth++) {
            element = new WeekCellController(LocalDate.of(firstDayOfMonth.getYear(), firstDayOfMonth.getMonth(), weekOfMonth * 7 - 6)).load();
            this.elements.add(element);
            this.grid.add(element, 0, weekOfMonth);
        }

        //TODO: Make all buttons functional
    }

    @Override
    void prev() {
        this.date = this.date.minusMonths(1);
        this.reload();
    }

    @Override
    void next() {
        this.date = this.date.plusMonths(1);
        this.reload();
    }

    /**
     * Controller for cells
     */
    private abstract static class CellController extends Controller {
        /**
         * Cell button
         */
        protected final @NotNull Button button;

        /**
         * Associated date
         */
        private final @NotNull LocalDate date;

        /**
         * Creates a new cell controller
         * @param date The associated date
         */
        public CellController(@NotNull LocalDate date, @NotNull Button button) {
            this.date = date;
            this.button = button;
        }

        @Override
        public @NotNull Parent load() {
            return this.button;
        }
    }

    /**
     * Controller for day cells
     */
    private static class DayCellController extends CellController {
        /**
         * Creates a new day cell controller
         * @param date The associated date
         */
        public DayCellController(@NotNull LocalDate date) {
            super(date, new Button(Integer.toString(date.getDayOfMonth())));
            this.button.getStyleClass().addAll("grid-cell-alt");
            this.button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    // TODO: Handle action
                }
            });
        }
    }

    /**
     * Controller for week cells
     */
    private static class WeekCellController extends CellController {
        /**
         * Creates a new day cell controller
         * @param date The associated date
         */
        public WeekCellController(@NotNull LocalDate date) {
            super(date, new Button(Integer.toString(date.get(WeekFields.ISO.weekOfYear()))));
            this.button.getStyleClass().addAll("grid-cell");
            this.button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    // TODO: Handle action
                }
            });
        }
    }
}