package commppetterm.gui.page;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDate;
import java.util.List;

public final class WeekPage extends PageController {
    @FXML
    private GridPane weekGrid;

    /**
     * Creates a new week subpage
     */
    public WeekPage() {
        super(null);
    }

    /**
     * Creates a new week subpage
     * @param date The date to display
     */
    public WeekPage(@Nullable LocalDate date) { super(date); }

    @Override
    void prev() {}

    @Override
    void next() {}

    @Override
    @NotNull List<DtfElement> pattern() {
        return List.of(
                new DtfElement(DtfElement.Type.PATTERN, "w"),
                new DtfElement(DtfElement.Type.LITERAL, ". KW "),
                new DtfElement(DtfElement.Type.PATTERN, "yyyy")
        );
    }
}
