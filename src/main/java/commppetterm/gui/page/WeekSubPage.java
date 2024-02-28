package commppetterm.gui.page;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public final class WeekSubPage extends PageController {
    @FXML
    private GridPane weekGrid;

    /**
     * Creates a new week subpage
     */
    public WeekSubPage() {
        super(DateTimeFormatter.ofPattern("w yyyy"), null);
    }

    /**
     * Creates a new week subpage
     * @param date The date to display
     */
    public WeekSubPage(@Nullable LocalDate date) { super(DateTimeFormatter.ofPattern("w yyyy"), date); }

    @Override
    void prev() {}

    @Override
    void next() {}
}
