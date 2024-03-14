package commppetterm.gui.page;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import commppetterm.database.Entry;
import commppetterm.gui.App;
import javafx.scene.Parent;
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
        int rowOffset = 0;
        int rowStart, rowSpan;
        int rowStep = 24 + rowOffset;

        /* Generate entries */
        List<Entry> entries = App.get().database().entries(App.get().date());
        List<Entry> wholeDayEntries = new LinkedList<>();

        for (Entry entry : entries) {
            if (entry.on(App.get().date())) {
                if (entry.end() != null) {
                    if (entry.start().toLocalDate().isBefore(App.get().date())) {
                        rowStart = 0;
                    } else {
                        rowStart = entry.start().getHour() + entry.start().getMinute() / 30;
                    }

                    if (entry.end().toLocalDate().isAfter(App.get().date())) {
                        rowSpan = 24 - rowStart;
                    } else {
                        rowSpan = entry.end().getHour() + (entry.end().getMinute() / 30 - rowStart);
                    }
                } else {
                    rowStart = 0;
                    rowSpan = 24;
                }

                if (rowStart == 0 && rowSpan == 24) {
                    wholeDayEntries.add(entry);
                } else {
                    rowStart += rowOffset;

                    if (rowSpan == 0) { rowSpan = 1; }

                    parent = new EntryController(entry).parent();
                    this.contents.add(parent);
                    this.entries.add(parent, colSpan, rowStart, 1, rowSpan);
                    colSpan++;
                }
            }
        }

        /* Generate whole day entries */
        for (Entry entry : wholeDayEntries) {
            parent = new EntryController(entry).parent();
            this.contents.add(parent);
            this.entries.add(parent, 1, rowStep, colSpan, 1);
            rowStep++;
        }
    }

    @Override
    @NotNull List<DtfElement> formatting() {
        return List.of(new DtfElement(DtfElement.Type.PATTERN, "dd.MM.yyyy"));
    }
}
