package commppetterm.gui.page;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import commppetterm.database.Entry;
import commppetterm.gui.App;
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
        List<Entry> singleEntries = new LinkedList<>();
        List<Entry> wholeEntries = new LinkedList<>();

        try {
            for (Entry entry : App.get().database().entries(date)) {
                if (entry.whole(date)) {
                    wholeEntries.add(entry);
                } else {
                    singleEntries.add(entry);
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

        for (Entry entry : singleEntries) {
            LocalTime startTime = entry.start(date);
            LocalTime endTime = entry.end(date);

            if (startTime != null && endTime != null) {
                rowStart = startTime.getHour() + startTime.getMinute() / 30;
                rowSpan = Math.max(endTime.getHour() + endTime.getMinute() / 30 - rowStart, 1);

                parent = new EntryController(entry).parent();
                this.contents.add(parent);
                this.entries.add(parent, colSpan, rowStart, 1, rowSpan);
                colSpan++;
            }
        }

        colSpan = Math.max(colSpan - 1, 1);

        for (Entry entry : wholeEntries) {
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
