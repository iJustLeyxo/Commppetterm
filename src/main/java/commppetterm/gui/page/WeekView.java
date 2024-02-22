package commppetterm.gui.page;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public final class WeekView extends Controller {
    @FXML
    private Label monthAndYearLabel;
    @FXML
    private GridPane weekGrid;

    public void initialize() {
        LocalDate today = LocalDate.now();
        updateWeekView(today);
    }

    private void updateWeekView(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        monthAndYearLabel.setText(date.format(formatter));
    }
}
