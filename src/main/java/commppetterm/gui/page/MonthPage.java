package commppetterm.gui.page;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.List;

import commppetterm.gui.App;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import org.jetbrains.annotations.NotNull;

/**
 * Page for showing months
 */
public final class MonthPage extends PageController {
    @FXML
    private GridPane grid;

    /**
     * Creates a new month page
     */
    public MonthPage() {}

    @Override
    @NotNull LocalDate prev(@NotNull LocalDate date) {
        return date.minusMonths(1);
    }

    @Override
    @NotNull LocalDate next(@NotNull LocalDate date) {
        return date.plusMonths(1);
    }

    @Override
    @NotNull List<DtfElement> formatting() {
        return List.of(new DtfElement(DtfElement.Type.PATTERN, "MMM yyyy"));
    }

    @Override
    protected void reload() {
        /* Clear grid */
        this.grid.getChildren().removeAll(contents);
        this.contents.clear();

        /* Generate */
        LocalDate iterator = LocalDate.of(App.get().date().getYear(), App.get().date().getMonth(), 1);
        Parent parent;
        int offset = 0;

        if (iterator.get(WeekFields.ISO.weekOfMonth()) == 0) { offset = 1; }

        do {
            /* Generate days */
            parent = new DayCellController(iterator).parent();
            this.contents.add(parent);
            this.grid.add(parent, iterator.getDayOfWeek().getValue(), iterator.get(WeekFields.ISO.weekOfMonth()) + offset);

            /* Generate weeks */
            if (iterator.getDayOfWeek().getValue() == 1 || iterator.getDayOfMonth() == 1) {
                parent = new WeekCellController(iterator).parent();
                this.contents.add(parent);
                this.grid.add(parent, 0, iterator.get(WeekFields.ISO.weekOfMonth()) + offset);
            }

            iterator = iterator.plusDays(1);
        } while (iterator.getDayOfMonth() != 1);
    }

    /**
     * Controller for day cells
     */
    public static class DayCellController extends CellController {
        /**
         * Creates a new day cell controller
         * @param date The associated date
         */
        public DayCellController(@NotNull LocalDate date) {
            super(new Button(Integer.toString(date.getDayOfMonth())));

            if (date.equals(LocalDate.now())) {
                this.element.getStyleClass().addAll("cell");
            } else  {
                this.element.getStyleClass().addAll("cell", "alt-color");
            }

            this.element.setOnAction(actionEvent -> {
                App.get().date(date);
                assert App.get().provider() != null;
                ((Calendar) App.get().provider()).swap(new DayPage());
            });
        }
    }

    /**
     * Controller for week cells
     */
    public static class WeekCellController extends CellController {
        /**
         * Creates a new day cell controller
         * @param date The date to associate to the week cell
         */
        public WeekCellController(@NotNull LocalDate date) {
            super(new Button(Integer.toString(date.get(WeekFields.ISO.weekOfYear()))));

            this.element.getStyleClass().addAll("cell");

            this.element.setOnAction(actionEvent -> {
                App.get().date(date);
                assert App.get().provider() != null;
                ((Calendar) App.get().provider()).swap(new WeekPage());
            });
        }
    }
}