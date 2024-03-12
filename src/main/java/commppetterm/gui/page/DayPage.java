package commppetterm.gui.page;

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
        Parent parent;
        int colIter = 1;

        for (Entry entry : Database.entries(App.date)) {
            EntryController controller  = new EntryController(entry);
            int rowStart, rowSpan;

            if (entry.end != null) {
                if (entry.start.getDayOfYear() < App.date.getDayOfYear() || entry.start.getYear() < App.date.getYear()) {
                    rowStart = 1;
                } else {
                    rowStart = entry.start.getHour() * 60 + entry.start.getMinute() + 1;
                }

                if (entry.start.getDayOfYear() > App.date.getDayOfYear() || entry.start.getYear() > App.date.getYear()) {
                    rowSpan = (24 * 60 + 1) - rowStart;
                } else {
                    rowSpan = (entry.end.getHour() * 60 + entry.end.getMinute() + 1) - rowStart;
                }
            } else {
                rowStart = 1;
                rowSpan = (24 * 60);
            }

            this.grid.add(controller.load(), colIter, rowStart, 1, rowSpan);
            this.contents.add(controller.parent());
            colIter++;
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
