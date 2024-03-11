package commppetterm.gui.page;

import java.util.LinkedList;
import java.util.List;

import commppetterm.database.Database;
import commppetterm.entity.Entry;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import org.jetbrains.annotations.NotNull;

import commppetterm.App;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

/**
 * Page for showing days
 */
public final class DayPage extends PageController {
    @FXML
    private GridPane grid;

    @Override
    protected void init() {
        this.reload();
    }

    @Override
    void prev() {
        App.date = App.date.minusDays(1);
        this.reload();
    }

    @Override
    void next() {
        App.date  = App.date.plusDays(1);
        this.reload();
    }

    @Override
    protected void reload() {
        /* Clear grid */
        this.grid.getChildren().removeAll(contents);
        this.contents.clear();

        /* Generate entries */
        int col = 1;

        for (Entry e : Database.entries(App.date)) {
            EntryController entry  = new EntryController(e);
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

            this.grid.add(entry.load(), col, start, 1, span);
            this.contents.add(entry.parent());
            col++;
        }
    }

    @Override
    @NotNull List<DtfElement> formatting() {
        return List.of(new DtfElement(DtfElement.Type.PATTERN, "dd.MM.yyyy"));
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
