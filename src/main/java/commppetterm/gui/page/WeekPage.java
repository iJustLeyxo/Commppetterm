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

    /**
     * List of all contents
     */
    private LinkedList<Parent> contents;

    /**
     * List of all entries
     */
    private LinkedList<EntryController> entries;

    @Override
    protected void init() {
        this.contents = new LinkedList<>();
        this.entries = new LinkedList<>();
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
        for (EntryController e : entries) {
            this.grid.getChildren().remove(e.parent());
        }

        this.grid.getChildren().removeAll(contents);
        this.contents = new LinkedList<>();

        /* Generate */
        LocalDate iter = App.date.minusDays(App.date.getDayOfWeek().getValue() - 1);
        Parent parent;

        do {
            /* Generate entries */
            int startCol = 1;
            int colSpan = 0;

            for (Entry e : Database.entries(iter)) {
                EntryController entry  = new EntryController(e);
                this.entries.add(entry);
                int start, span;

                if (entry.entry.end != null) {
                    if (entry.entry.start.getDayOfYear() < App.date.getDayOfYear() || entry.entry.start.getYear() < App.date.getYear()) {
                        start = 1;
                    } else {
                        start = entry.entry.start.getHour() * 60 + entry.entry.start.getMinute() + 1;
                    }

                    if (entry.entry.start.getDayOfYear() > App.date.getDayOfYear() || entry.entry.start.getYear() > App.date.getYear()) {
                        span = (24 * 60 + 1) - start;
                    } else {
                        span = (entry.entry.end.getHour() * 60 + entry.entry.end.getMinute() + 1) - start;
                    }
                } else {
                    start = 1;
                    span = (24 * 60);
                }

                this.grid.add(entry.load(), startCol + colSpan, start, 1, span);
                colSpan++;
            }

            /* Generate day cell */
            parent = new DayCellController(iter).load();
            this.contents.add(parent);
            this.grid.add(parent, startCol, 0, colSpan + 1, 1);

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

            this.button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    App.entry = entry;
                }
            });
        }
    }
}
