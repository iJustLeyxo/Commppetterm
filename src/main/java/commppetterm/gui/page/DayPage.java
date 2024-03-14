package commppetterm.gui.page;

import java.time.LocalDate;
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
    private GridPane grid;

    /**
     * Initializes a new day page
     */
    public DayPage() {
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
        int colStep = 1;
        int rowOffset = 0;
        int rowStart, rowSpan;

        for (Entry entry : App.get().database().entries(App.get().date())) {
            if (entry.on(App.get().date())) {
                if (entry.end() != null) {
                    if (entry.start().getDayOfYear() < App.get().date().getDayOfYear() || entry.start().getYear() < App.get().date().getYear()) {
                        rowStart = 0;
                    } else {
                        rowStart = entry.start().getHour() + entry.start().getMinute() / 30;
                    }

                    if (entry.end().getDayOfYear() > App.get().date().getDayOfYear() || entry.end().getYear() > App.get().date().getYear()) {
                        rowSpan = 24 - rowStart;
                    } else {
                        rowSpan = entry.end().getHour() + (entry.end().getMinute() / 30 - rowStart);
                    }
                } else {
                    rowStart = 0;
                    rowSpan = 24;
                }

                rowStart += rowOffset;

                // TODO: Detect full day entries

                parent = new EntryController(entry).parent();
                this.contents.add(parent);
                this.grid.add(parent, colStep, rowStart, 1, rowSpan);
                colStep++;
            }
        }
    }

    @Override
    @NotNull List<DtfElement> formatting() {
        return List.of(new DtfElement(DtfElement.Type.PATTERN, "dd.MM.yyyy"));
    }
}
