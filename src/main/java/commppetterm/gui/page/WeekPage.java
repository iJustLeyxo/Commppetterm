package commppetterm.gui.page;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.LinkedList;
import java.util.List;

import commppetterm.database.Database;
import commppetterm.entity.Entry;
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

    @Override
    protected void init() {
        this.reload();
    }

    @Override
    void prev() {
        App.date = App.date.minusWeeks(1);
        this.reload();
    }

    @Override
    void next() {
        App.date = App.date.plusWeeks(1);
        this.reload();
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
    protected void reload() {
        /* Clear grid */
        this.grid.getChildren().removeAll(contents);
        this.contents.clear();

        /* Generate */
        LocalDate iter = App.date.minusDays(App.date.getDayOfWeek().getValue() - 1);
        Parent parent;
        int colStep = 1;
        int colSpan = 0;

        do {
            /* Generate entries */
            for (Entry entry : Database.entries(iter)) {
                EntryController controller  = new EntryController(entry);
                int rowStart, rowSpan;

                if (entry.end != null) {
                    if (entry.start.toLocalDate().isBefore(App.date)) {
                        rowStart = 1;
                    } else {
                        rowStart = entry.start.getHour() * 60 + entry.start.getMinute() + 1;
                    }

                    if (entry.end.toLocalDate().isAfter(App.date)) {
                        rowSpan = (24 * 60 + 1) - rowStart;
                    } else {
                        rowSpan = (entry.end.getHour() * 60 + entry.end.getMinute() + 1) - rowStart;
                    }
                } else {
                    rowStart = 1;
                    rowSpan = (24 * 60);
                }

                this.grid.add(controller.load(), colStep + rowSpan, rowStart, 1, rowSpan);
                this.contents.add(controller.parent());
                colSpan++;
            }

            /* Generate day cell */
            parent = new DayCellController(iter).load();
            this.contents.add(parent);
            this.grid.add(parent, colStep, 0, colSpan + 1, 1);

            /* Increment iterators */
            colStep = colStep + colSpan + 1;
            colSpan = 0;
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
            super(new Button(date.getDayOfWeek().getDisplayName(TextStyle.SHORT, App.locale) + "\n" + date.getDayOfMonth()));

            if (date.equals(LocalDate.now())) {
                this.element.getStyleClass().addAll("cell");
            } else  {
                this.element.getStyleClass().addAll("cell", "alt-color");
            }

            this.element.setOnAction(new EventHandler<ActionEvent>() {
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
     * Controller for day cells
     */
    public static class EntryController extends CellController {
        /**
         * Associated entry
         */
        private @NotNull final Entry entry;

        /**
         * Creates a new day cell controller
         * @param entry The associated entry
         */
        public EntryController(@NotNull Entry entry) {
            super(new Button(entry.title));
            this.entry = entry;

            this.element.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    App.entry = entry;
                }
            });
        }
    }
}
