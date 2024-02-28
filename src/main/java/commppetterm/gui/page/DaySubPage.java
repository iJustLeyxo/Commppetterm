package commppetterm.gui.page;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

import java.time.format.DateTimeFormatter;

public final class DaySubPage extends PageController {
    @FXML
    private GridPane dayGrid;

    /**
     * Creates a new subpage
     */
    public DaySubPage() {
        super(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @Override
    void prev() {}

    @Override
    void next() {}
}
