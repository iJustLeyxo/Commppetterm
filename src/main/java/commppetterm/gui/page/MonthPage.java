package commppetterm.gui.page;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.LinkedList;
import java.util.List;

import commppetterm.App;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Page for showing months
 */
public final class MonthPage extends PageController {
    @FXML
    private GridPane grid;

    /**
     * List of all contents in  the grid
     */
    private LinkedList<Parent> contents;

    /**
     * Creates a new month subpage
     */
    public MonthPage() {
        super(null);
    }

    /**
     * Creates a new month subpage
     * @param date The date to display
     */
    public MonthPage(@Nullable LocalDate date) {
        super(date);
    }

    @Override
    protected void init() {
        this.contents = new LinkedList<>();
        this.generate();
    }

    @Override
    void prev() {
        this.date = this.date.minusMonths(1);
        this.generate();
    }

    @Override
    void next() {
        this.date = this.date.plusMonths(1);
        this.generate();
    }

    @Override
    @NotNull List<DtfElement> formatting() {
        return List.of(new DtfElement(DtfElement.Type.PATTERN, "MMM yyyy"));
    }

    @Override
    protected void generate() {
        /* Clear grid pane */
        this.grid.getChildren().removeAll(contents);
        this.contents = new LinkedList<>();

        /* Generate */
        LocalDate iter = LocalDate.of(this.date.getYear(), this.date.getMonth(), 1);
        Parent parent = null;
        int offset = 0;

        if (iter.get(WeekFields.ISO.weekOfMonth()) == 0) { offset = 1; }

        do {
            /* Generate days */
            parent = new DayCellController(iter).load();
            this.contents.add(parent);
            this.grid.add(parent, iter.getDayOfWeek().getValue(), iter.get(WeekFields.ISO.weekOfMonth()) + offset);

            /* Generate weeks */
            if (iter.getDayOfWeek().getValue() == 1 || iter.getDayOfMonth() == 1) {
                parent = new WeekCellController(iter).load();
                this.contents.add(parent);
                this.grid.add(parent, 0, iter.get(WeekFields.ISO.weekOfMonth()) + offset);
            }

            iter = iter.plusDays(1);
        } while (iter.getDayOfMonth() != 1);
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
        protected final @NotNull LocalDate date;

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

            if (this.date.equals(LocalDate.now())) {
                this.button.getStyleClass().addAll("cell");
            } else  {
                this.button.getStyleClass().addAll("cell", "alt-color");
            }

            this.button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    DayPage page = new DayPage(date);
                    try {
                        Calendar.get().swap(page);
                    } catch (Exception e) {
                        App.logger.severe(e.toString());
                        e.printStackTrace(System.out);
                    }
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
            this.button.getStyleClass().addAll("cell");
            this.button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    WeekPage page = new WeekPage(date);
                    try {
                        Calendar.get().swap(page);
                    } catch (Exception e) {
                        App.logger.severe(e.toString());
                        e.printStackTrace(System.out);
                    }
                }
            });
        }
    }
}