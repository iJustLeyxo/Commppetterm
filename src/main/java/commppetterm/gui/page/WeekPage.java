package commppetterm.gui.page;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
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
        List<Entry> singleEntries = new LinkedList<>();
        List<Triple<Entry, Integer, Integer>> wholeEntries = new LinkedList<>();

        try {
            for (Entry entry : App.get().database().entries(start, end)) {
                if (entry.whole(start, end) || entry.untimed()) {
                    wholeEntries.add(new Triple<>(entry, 0, 0));
                } else {
                    singleEntries.add(entry);
                }
            }
        } catch (SQLException e) {
            App.get().controller(new Settings(e));
        }

        do {
            for (Triple<Entry, Integer, Integer> entry : wholeEntries) {
                if (entry.a().on(iter)) {
                    if (entry.b() < 1) {
                        entry.b(colStep);
                        entry.c(1);
                    } else {
                        entry.c(entry.c() + 1);
                    }
                }
            }

            for (Entry entry : singleEntries) {
                LocalTime startTime = entry.start(iter);
                LocalTime endTime = entry.end(iter);

                if (startTime != null && endTime != null) {
                    rowStart = startTime.getHour() + startTime.getMinute() / 30;
                    rowSpan = Math.max(endTime.getHour() + endTime.getMinute() / 30 - rowStart, 1);
                    rowStart += rowOffset;

                    parent = new EntryController(entry).parent();
                    this.contents.add(parent);
                    this.entries.add(parent, colStep + colSpan, rowStart, 1, rowSpan);
                    colSpan++;
                }
            }

            colSpan = Math.max(colSpan, 1);

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
            this.entries.add(parent, entry.b(), rowStep, Math.max(entry.c(), 1), 1);
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
