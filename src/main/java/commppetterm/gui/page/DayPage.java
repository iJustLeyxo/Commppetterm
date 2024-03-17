package commppetterm.gui.page;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import commppetterm.database.Entry;
import commppetterm.gui.App;
import commppetterm.util.Triple;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.jetbrains.annotations.NotNull;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

/**
 * Page for showing days
 */
public final class DayPage extends PageController {
    @FXML
    private GridPane entries;

    @Override
    @NotNull LocalDate prev(@NotNull LocalDate date) {
        return date.minusDays(1);
    }

    @Override
    @NotNull LocalDate next(@NotNull LocalDate date) {
        return date.plusDays(1);
    }

    @Override
    protected void reload() {
        /* Clear grid */
        this.entries.getChildren().removeAll(contents);
        this.contents.clear();

        /* Generate */
        Parent parent;
        int colSpan = 1;
        int rowStart, rowSpan;
        int rowStep = 24;

        /* Generate entries */
        LocalDate date = App.get().date();
        List<Entry> partialEntries = new LinkedList<>();
        List<Entry> discreteEntries = new LinkedList<>();

        /* Get and sort entries */
        try {
            for (Entry entry : App.get().database().entries(date)) {
                if (entry.whole(date)) {
                    discreteEntries.add(entry);
                } else {
                    partialEntries.add(entry);
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

        /* Compact entries */
        List<List<Triple<Entry, LocalTime, LocalTime>>> columns = new LinkedList<>();
        columns.add(new LinkedList<>());

        for (Entry entry : partialEntries) {
            if (entry.on(date)) {
                LocalTime startTime = entry.start(date);
                LocalTime endTime = entry.end(date);

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

                parent = new EntryController(cell.a()).parent();
                this.contents.add(parent);
                this.entries.add(parent, colSpan, rowStart, 1, rowSpan);
            }

            colSpan++;
        }

        /* Place discrete entries */
        for (Entry entry : discreteEntries) {
            parent = new EntryController(entry).parent();
            this.contents.add(parent);
            this.entries.add(parent, 1, rowStep, colSpan, 1);
            rowStep++;
        }
    }

    @Override
    @NotNull List<DtfElement> formatting() {
        return List.of(new DtfElement(DtfElement.Type.PATTERN, "E dd.MM.yyyy"));
    }
}
