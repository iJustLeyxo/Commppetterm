package commppetterm.gui.page;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDate;
import java.util.List;

/**
 * Page for showing days
 */
public final class DayPage extends PageController {
    @FXML
    private GridPane dayGrid;

    /**
     * Creates a new day page
     */
    public DayPage() { super(null); }

    /**
     * Creates a new day page
     * @param date The date to display
     */
    public DayPage(@Nullable LocalDate date) {
        super(date);
    }

    @Override
    void prev() {}

    @Override
    void next() {}

    @Override
    @NotNull List<DtfElement> formatting() {
        return List.of(new DtfElement(DtfElement.Type.PATTERN, "dd.MM.yyyy"));
    }
}
