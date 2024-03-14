package commppetterm.gui.page;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.LinkedList;
import java.util.List;

import commppetterm.database.Entry;
import commppetterm.gui.App;
import commppetterm.gui.exception.FxmlLoadException;
import commppetterm.gui.exception.URLNotFoundException;
import commppetterm.util.Triple;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import org.jetbrains.annotations.NotNull;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
 

/**
 * Page for showing weeks
 */
public final class WeekPage extends PageController {
    @FXML
    private GridPane entries;

    /**
     * Initializes a new week page
     */
    public WeekPage() throws URLNotFoundException, FxmlLoadException {}

    @Override
    @NotNull LocalDate prev(@NotNull LocalDate date) {
        return date.minusWeeks(1);
    }

    @Override
    @NotNull LocalDate next(@NotNull LocalDate date) {
        return date.plusWeeks(1);
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
        this.entries.getChildren().removeAll(contents);
        this.contents.clear();

        /* Generate */
        LocalDate start = App.get().date().minusDays(App.get().date().getDayOfWeek().getValue() - 1);
        LocalDate end = start.plusDays(6);
        LocalDate iter = start;
        Parent parent;
        int colStep = 1;
        int colSpan = 0;
        int rowOffset = 1;
        int rowStart, rowSpan;
        int rowStep = 24 + rowOffset;

        /* Generate entries */
        List<Entry> entries = App.get().database().entries(start, end);
        List<Triple<Entry, Integer, Integer>> wholeEntries = new LinkedList<>();

        for (Entry entry : entries) {
            if (entry.start().toLocalDate().isBefore(start) && entry.end().toLocalDate().isAfter(end) || !entry.timed()) {
                entries.remove(entry);
                wholeEntries.add(new Triple<>(entry, -1, 0));
            }
        }

        do {
            for (Triple<Entry, Integer, Integer> entry : wholeEntries) {
                if (entry.a().on(iter)) {
                    if (entry.b() < 0) {
                        entry.b(colStep);
                        entry.c(1);
                    } else {
                        entry.c(entry.c() + 1);
                    }
                }
            }

            for (Entry entry : entries) {
                if (entry.on(iter)) {
                    if (entry.end() != null) {
                        if (entry.start().toLocalDate().isBefore(iter)) {
                            rowStart = 0;
                        } else {
                            rowStart = entry.start().getHour() + entry.start().getMinute() / 30;
                        }

                        if (entry.end().toLocalDate().isAfter(iter)) {
                            rowSpan = 24 - rowStart;
                        } else {
                            rowSpan = entry.end().getHour() + entry.end().getMinute() / 30 - rowStart;
                        }
                    } else {
                        rowStart = 0;
                        rowSpan = 24;
                    }

                    if (rowStart == 0 && rowSpan == 24) {
                        Triple<Entry, Integer, Integer> temp = new Triple<>(entry, colStep, colSpan);

                        if (!temp.hasA(wholeEntries)) {
                            wholeEntries.add(temp);
                        }
                    } else {
                        rowStart += rowOffset;

                        if (rowSpan == 0) {
                            rowSpan = 1;
                        }

                        parent = new EntryController(entry).parent();
                        this.contents.add(parent);
                        this.entries.add(parent, colStep + colSpan, rowStart, 1, rowSpan);
                        colSpan++;
                    }
                }
            }

            if (colSpan == 0) { colSpan = 1; }

            rowStep = 24 + rowOffset;

            /* Generate day cell */
            parent = new DayCellController(iter).parent();
            this.contents.add(parent);
            this.entries.add(parent, colStep, 0, colSpan, 1);

            /* Increment iterators */
            colStep = colStep + colSpan;
            colSpan = 0;
            iter = iter.plusDays(1);
        } while (iter.getDayOfWeek().getValue() != 1);

        /* Generate whole day entries */
        for (Triple<Entry, Integer, Integer> entry : wholeEntries) {
            parent = new EntryController(entry.a()).parent();
            this.contents.add(parent);
            this.entries.add(parent, entry.b(), rowStep, entry.c(), 1);
            rowStep++;
        }
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
            super(new Button(date.getDayOfWeek().getDisplayName(TextStyle.SHORT, App.get().LOCALE) + "\n" + date.getDayOfMonth()));

            this.element.setMaxSize(Double.MAX_VALUE, 50);

            if (date.equals(LocalDate.now())) {
                this.element.getStyleClass().addAll("cell");
            } else  {
                this.element.getStyleClass().addAll("cell", "alt-color");
            }

            this.element.setOnAction(actionEvent -> {
                try {
                    App.get().date(date);
                    assert App.get().controller() != null;
                    ((Calendar) App.get().controller()).swap(new DayPage());
                } catch (Exception e) {
                    App.get().LOGGER.severe(e.toString());
                    e.printStackTrace(System.out);
                }
            });
        }
    }
}
