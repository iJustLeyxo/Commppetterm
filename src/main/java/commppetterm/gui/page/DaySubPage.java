package commppetterm.gui.page;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

public final class DaySubPage extends SubPage {
    @FXML
    private Label monthAndYearLabel;
    
    @FXML
    private GridPane weekGrid;

    public void initialize() {
        
    }

    @Override
    @NotNull LocalDate getDate() {
        return LocalDate.now();
    }
}
