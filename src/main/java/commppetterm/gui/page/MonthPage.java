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

/**
 * Page for showing months
 */
public final class MonthPage extends PageController {
    @FXML
    private GridPane gridPane;

    /**
     * List of all contents
     */
    private LinkedList<Parent> contents;

    @Override
    protected void init() {
        this.contents = new LinkedList<>();
        this.generate();
    }

    @Override
    void prev() {
        App.date = App.date.minusMonths(1);
        this.generate();
    }

    @Override
    void next() {
        App.date = App.date.plusMonths(1);
        this.generate();
    }

    @Override
    @NotNull List<DtfElement> formatting() {
        return List.of(new DtfElement(DtfElement.Type.PATTERN, "MMM yyyy"));
    }

    @Override
    protected void generate() {
        /* Clear grid */
        this.gridPane.getChildren().removeAll(contents);
        this.contents = new LinkedList<>();

        /* Generate */
        LocalDate iter = LocalDate.of(App.date.getYear(), App.date.getMonth(), 1);
        Parent parent;
        int offset = 0;

        if (iter.get(WeekFields.ISO.weekOfMonth()) == 0) { offset = 1; }

        do {
            /* Generate days */
            parent = new DayCellController(iter).load();
            this.contents.add(parent);
            this.gridPane.add(parent, iter.getDayOfWeek().getValue(), iter.get(WeekFields.ISO.weekOfMonth()) + offset);

            /* Generate weeks */
            if (iter.getDayOfWeek().getValue() == 1 || iter.getDayOfMonth() == 1) {
                parent = new WeekCellController(iter).load();
                this.contents.add(parent);
                this.gridPane.add(parent, 0, iter.get(WeekFields.ISO.weekOfMonth()) + offset);
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
                this.button.getStyleClass().addAll("cell");
            } else  {
                this.button.getStyleClass().addAll("cell", "alt-color");
            }

            this.button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    DayPage page = new DayPage();
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
    public static class WeekCellController extends CellController {
        /**
         * Creates a new day cell controller
         */
        public WeekCellController(@NotNull LocalDate date) {
            super(new Button(Integer.toString(date.get(WeekFields.ISO.weekOfYear()))));
            this.button.getStyleClass().addAll("cell");
            this.button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    WeekPage page = new WeekPage();
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