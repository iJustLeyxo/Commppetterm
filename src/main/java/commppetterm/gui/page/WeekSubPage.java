package commppetterm.gui.page;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import org.jetbrains.annotations.NotNull;

import java.time.format.DateTimeFormatter;

public final class WeekSubPage extends SubPage {
    @FXML
    private GridPane weekGrid;

    /**
     * Creates a new week subpage
     */
    public WeekSubPage() {
        super(DateTimeFormatter.ofPattern("w "));
    }

    @Override
    void prev() {}

    @Override
    void next() {}
}
