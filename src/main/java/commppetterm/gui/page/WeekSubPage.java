package commppetterm.gui.page;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

public final class WeekSubPage extends SubPage {
    @FXML
    private GridPane weekGrid;

    @Override
    @NotNull String prev() {
        return "N/A";
    }

    @Override
    @NotNull String next() {
        return "N/A";
    }
}
