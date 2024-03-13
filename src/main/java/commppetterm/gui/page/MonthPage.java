package commppetterm.gui.page;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.List;

import commppetterm.gui.App;
import commppetterm.gui.exception.FxmlLoadException;
import commppetterm.gui.exception.URLNotFoundException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
     * Initializes a new month page
     */
    public MonthPage() {
        this.reload();
    }

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
        LocalDate iter = LocalDate.of(App.get().date().getYear(), App.get().date().getMonth(), 1);
        Parent parent;
        int offset = 0;

        if (iter.get(WeekFields.ISO.weekOfMonth()) == 0) { offset = 1; }

        do {
            /* Generate days */
            parent = new DayCellController(iter).parent();
            this.contents.add(parent);
            this.grid.add(parent, iter.getDayOfWeek().getValue(), iter.get(WeekFields.ISO.weekOfMonth()) + offset);

            /* Generate weeks */
            if (iter.getDayOfWeek().getValue() == 1 || iter.getDayOfMonth() == 1) {
                parent = new WeekCellController(iter).parent();
                this.contents.add(parent);
                this.grid.add(parent, 0, iter.get(WeekFields.ISO.weekOfMonth()) + offset);
            }

            iter = iter.plusDays(1);
        } while (iter.getDayOfMonth() != 1);
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

            this.element.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    try {
                        App.get().date(date);
                        assert App.get().controller() != null;
                        ((Calendar) App.get().controller()).swap(new DayPage());
                    } catch (Exception e) {
                        App.get().LOGGER.severe(e.toString());
                        e.printStackTrace(System.out);
                    }
                }
            });
        }
    }

    /**
     * Controller for week cells
     */
    public static class WeekCellController extends CellController {
        /**
         * Creates a new day cell controller
         */
        public WeekCellController(@NotNull LocalDate date) {
            super(new Button(Integer.toString(date.get(WeekFields.ISO.weekOfYear()))));
            this.element.getStyleClass().addAll("cell");
            this.element.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    WeekPage page = null;
                    try {
                        page = new WeekPage();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        assert App.get().controller() != null;
                        ((Calendar) App.get().controller()).swap(page);
                    } catch (Exception e) {
                        App.get().LOGGER.severe(e.toString());
                        e.printStackTrace(System.out);
                    }
                }
            });
        }
    }
}