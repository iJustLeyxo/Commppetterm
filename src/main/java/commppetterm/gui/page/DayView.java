package commppetterm.gui.page;

// import java.time.LocalDate;
// import java.time.format.DateTimeFormatter;

import org.jetbrains.annotations.NotNull;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public final class DayView implements Controller {
    @Override
    public @NotNull String path() {
        return "/commppetterm/gui/page/DayView.fxml";
    }

    @FXML
    private Label monthAndYearLabel;
    @FXML
    private GridPane weekGrid;

    public void initialize() {
        
    }
}
