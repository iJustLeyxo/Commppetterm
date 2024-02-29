package commppetterm.gui.page;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

public final class DaySubPage extends SubPageController {
    @FXML
    private GridPane dayGrid;

    /**
     * Creates a new day page
     */
    public DaySubPage() { super(null); }

    /**
     * Creates a new day page
     * @param date The date to display
     */
    public DaySubPage(@Nullable LocalDate date) {
        super(date);
    }

    @Override
    void prev() {}

    @Override
    void next() {}

    @Override
    @NotNull List<DtfElement> pattern() {
        return List.of(new DtfElement(DtfElement.Type.PATTERN, "dd.MM.yyyy"));
    }
}
