package commppetterm.gui.page;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.LinkedList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import org.jetbrains.annotations.NotNull;

import commppetterm.App;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
 

/**
 * Page for showing weeks
 */
public final class WeekPage extends PageController {
    @FXML
    private GridPane grid;

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
        App.date = App.date.minusWeeks(1);
        this.generate();
    }

    @Override
    void next() {
        App.date = App.date.plusWeeks(1);
        this.generate();
    }

    @Override
    @NotNull List<DtfElement> formatting() {
        return List.of(
                new DtfElement(DtfElement.Type.PATTERN, "w"),
                new DtfElement(DtfElement.Type.LITERAL, ". KW "),
                new DtfElement(DtfElement.Type.PATTERN, "yyyy")
        );
    }

    @Override
    protected void generate() {
        /* Clear grid */
        this.grid.getChildren().removeAll(contents);
        this.contents = new LinkedList<>();

        /* Generate */
        LocalDate iter = App.date.minusDays(App.date.getDayOfWeek().getValue() - 1);
        Parent parent;

        do {
            /* Generate day cells */
            parent = new DayCellController(iter).load();
            this.contents.add(parent);
            this.grid.add(parent, iter.getDayOfWeek().getValue(), 0);

            iter = iter.plusDays(1);
        } while (iter.getDayOfWeek().getValue() != 1);
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
                this.button.getStyleClass().addAll("cell-width", "small-cell-height");
            } else  {
                this.button.getStyleClass().addAll("cell-width", "small-cell-height", "alt-color");
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
}
