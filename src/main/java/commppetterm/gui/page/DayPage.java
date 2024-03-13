package commppetterm.gui.page;

import java.time.LocalDate;
import java.util.List;

import commppetterm.database.Database;
import commppetterm.database.Entry;
import commppetterm.gui.App;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import org.jetbrains.annotations.NotNull;

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
        this.grid.getChildren().removeAll(contents);
        this.contents.clear();

        /* Generate entries */
        Parent parent;
        int colIter = 1;

        for (Entry entry : App.get().database().dayEntries(App.get().date())) {
            EntryController controller  = new EntryController(entry);
            int rowStart, rowSpan;

            if (entry.end() != null) {
                if (entry.start().getDayOfYear() < App.get().date().getDayOfYear() || entry.start().getYear() < App.get().date().getYear()) {
                    rowStart = 1;
                } else {
                    rowStart = entry.start().getHour() * 60 + entry.start().getMinute() + 1;
                }

                if (entry.start().getDayOfYear() > App.get().date().getDayOfYear() || entry.start().getYear() > App.get().date().getYear()) {
                    rowSpan = (24 * 60 + 1) - rowStart;
                } else {
                    rowSpan = (entry.end().getHour() * 60 + entry.end().getMinute() + 1) - rowStart;
                }
            } else {
                rowStart = 1;
                rowSpan = (24 * 60);
            }

            // TODO: Detect full day entries

            this.grid.add(controller.load(), colIter, rowStart, 1, rowSpan);
            this.contents.add(controller.parent());
            colIter++;
        }
    }

    @Override
    @NotNull List<DtfElement> formatting() {
        return List.of(new DtfElement(DtfElement.Type.PATTERN, "dd.MM.yyyy"));
    }
}
