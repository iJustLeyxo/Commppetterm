package commppetterm.gui.page;

import java.time.LocalDate;
import java.util.List;

import org.jetbrains.annotations.NotNull;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

/**
 * Page for showing weeks
 */
public final class WeekPage extends PageController {
    @FXML
    private GridPane weekGrid;

    @Override
    void prev() {
        this.date = this.date.minusWeeks(1);
        this.generate();
    }

    @Override
    void next() {
        this.date = this.date.plusWeeks(1);
        this.generate();
    }

    @Override
    @NotNull List<DtfElement> formatting() {
        return List.of(
                new DtfElement(DtfElement.Type.PATTERN, "w"),
                new DtfElement(DtfElement.Type.LITERAL, ". KW "),
                new DtfElement(DtfElement.Type.PATTERN, "yyyy")
        );
    }
}
