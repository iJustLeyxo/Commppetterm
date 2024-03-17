package commppetterm.gui.page;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import commppetterm.database.Entry;
import commppetterm.gui.App;
import commppetterm.util.Triple;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
    public WeekPage() {}

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

        /* Utils */
        LocalDate start = App.get().date().minusDays(App.get().date().getDayOfWeek().getValue() - 1);
        LocalDate end = start.plusDays(6);
        LocalDate iterator = start;
        Parent parent;
        int colStep = 1;
        int colSpan = 0;
        int rowOffset = 1;
        int rowStart, rowSpan;
        int rowStep = 24 + rowOffset;

        /* Entries */
        List<Entry> partialEntries = new LinkedList<>();
        List<Triple<Entry, Integer, Integer>> discreteEntries = new LinkedList<>();

        /* Get and sort entries */
        try {
            for (Entry entry : App.get().database().entries(start, end)) {
                if (entry.on(start, end)) {
                    if (entry.whole(start, end) || entry.untimed()) {
                        discreteEntries.add(new Triple<>(entry, 0, 0));
                    } else {
                        partialEntries.add(entry);
                    }
                }
            }
        } catch (SQLException e) {
            Optional<ButtonType> res = App.get().alert(e, Alert.AlertType.ERROR, "Go to settings?", ButtonType.YES, ButtonType.NO);

            if (res.isPresent() && res.get().equals(ButtonType.YES)) {
                App.get().provider(new Settings());
            } else {
                return;
            }
        }

        do {
            /* Compact partial entries */
            List<List<Triple<Entry, LocalTime, LocalTime>>> columns = new LinkedList<>();
            columns.add(new LinkedList<>());

            for (Entry entry : partialEntries) {
                if (entry.on(iterator)) {
                    LocalTime startTime = entry.start(iterator);
                    LocalTime endTime = entry.end(iterator);

                    boolean fitted = false;

                    for (List<Triple<Entry, LocalTime, LocalTime>> column : columns) {
                        boolean fits = true;

                        for (Triple<Entry, LocalTime, LocalTime> cell : column) {
                            if (startTime.isAfter(cell.b()) && startTime.isBefore(cell.c()) ||
                                    endTime.isAfter(cell.b()) && endTime.isBefore(cell.c()) ||
                                    startTime.isBefore(cell.b()) && endTime.isAfter(cell.c())) {
                                fits = false;
                                break;
                            }
                        }

                        if (fits) {
                            column.add(new Triple<>(entry, startTime, endTime));
                            fitted = true;
                        }
                    }

                    if (!fitted) {
                        List<Triple<Entry, LocalTime, LocalTime>> column = new LinkedList<>();
                        columns.add(column);
                        column.add(new Triple<>(entry, startTime, endTime));
                    }
                }
            }

            /* Place partial entries */
            for (List<Triple<Entry, LocalTime, LocalTime>> column : columns) {
                for (Triple<Entry, LocalTime, LocalTime> cell : column) {
                    rowStart = cell.b().getHour() + cell.b().getMinute() / 30;
                    rowSpan = Math.max(cell.c().getHour() + cell.c().getMinute() / 30 - rowStart, 1);
                    rowStart += rowOffset;

                    parent = new EntryController(cell.a()).parent();
                    this.contents.add(parent);
                    this.entries.add(parent, colStep + colSpan, rowStart, 1, rowSpan);
                }

                colSpan++;
            }

            /* Update discrete entries */
            for (Triple<Entry, Integer, Integer> entry : discreteEntries) {
                if (entry.a().on(iterator)) {
                    if (entry.b() < 1) {
                        entry.b(colStep);
                    }

                    entry.c(entry.c() + colSpan);
                }
            }

            /* Generate day cell */
            parent = new DayCellController(iterator).parent();
            this.contents.add(parent);
            this.entries.add(parent, colStep, 0, colSpan, 1);

            /* Increment iterators */
            colStep = colStep + colSpan;
            colSpan = 0;
            iterator = iterator.plusDays(1);
        } while (iterator.getDayOfWeek().getValue() != 1);

        /* Compact discrete entries */
        List<List<Triple<Entry, Integer, Integer>>> rows = new LinkedList<>();
        rows.add(new LinkedList<>());

        for (Triple<Entry, Integer, Integer> entry : discreteEntries) {
            boolean fitted = false;

            for (List<Triple<Entry, Integer, Integer>> row : rows) {
                boolean fits = true;

                for (Triple<Entry, Integer, Integer> cell : row) {
                    if (entry.b() > cell.b() && entry.b() < cell.b() + cell.c() - 1 ||
                            entry.c() > cell.b() && entry.c() < cell.b() + cell.c() - 1 ||
                            entry.b() < cell.b() && entry.c() > cell.c()) {
                        fits = false;
                        break;
                    }
                }

                if (fits) {
                    row.add(entry);
                    fitted = true;
                }
            }

            if (!fitted) {
                List<Triple<Entry, Integer, Integer>> row = new LinkedList<>();
                rows.add(row);
                row.add(entry);
            }
        }

        /* Place discrete entries */
        for (List<Triple<Entry, Integer, Integer>> row : rows) {
            for (Triple<Entry, Integer, Integer> cell : row) {
                parent = new EntryController(cell.a()).parent();
                this.contents.add(parent);
                this.entries.add(parent, cell.b(), rowStep, cell.c(), 1);
            }

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
                App.get().date(date);
                assert App.get().provider() != null;
                ((Calendar) App.get().provider()).swap(new DayPage());
            });
        }
    }
}
