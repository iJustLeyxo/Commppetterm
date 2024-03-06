package commppetterm.gui.page;

import java.util.List;

import org.jetbrains.annotations.NotNull;

import commppetterm.App;
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
        App.date = App.date.minusDays(1);
        this.generate();
    }

    @Override
    void next() {
        App.date  = App.date.plusDays(1);
        this.generate();
    }

    @Override
    @NotNull List<DtfElement> formatting() {
        return List.of(new DtfElement(DtfElement.Type.PATTERN, "dd.MM.yyyy"));
    }


}
