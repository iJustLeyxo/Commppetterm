package commppetterm.gui.page;

import java.time.LocalDate;
import java.util.List;

import org.jetbrains.annotations.NotNull;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

/**
 * Page for showing days
 */
public final class DayPage extends PageController {
    @FXML
    private GridPane dayGrid;

    @Override
    void prev() {
        this.date = this.date.minusDays(1);
        this.generate();
    }

    @Override
    void next() {
        this.date  = this.date.plusDays(1);
        this.generate();
    }

    @Override
    @NotNull List<DtfElement> formatting() {
        return List.of(new DtfElement(DtfElement.Type.PATTERN, "dd.MM.yyyy"));
    }
}
