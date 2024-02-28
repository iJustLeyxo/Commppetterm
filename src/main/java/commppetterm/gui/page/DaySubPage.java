package commppetterm.gui.page;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public final class DaySubPage extends SubPageController {
    @FXML
    private GridPane dayGrid;

    /**
     * Creates a new day page
     */
    public DaySubPage() { super(DateTimeFormatter.ofPattern("dd.MM.yyyy"), null); }

    /**
     * Creates a new day page
     * @param date The date to display
     */
    public DaySubPage(@Nullable LocalDate date) {
        super(DateTimeFormatter.ofPattern("dd.MM.yyyy"), date);
    }

    @Override
    void prev() {}

    @Override
    void next() {}
}
