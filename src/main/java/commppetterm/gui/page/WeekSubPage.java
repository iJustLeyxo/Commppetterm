package commppetterm.gui.page;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

public final class WeekSubPage extends SubPage {
    @FXML
    private Label monthAndYearLabel;

    @FXML
    private GridPane weekGrid;

    @Override
    @NotNull LocalDate getDate() {
        return LocalDate.now();
    }
}
