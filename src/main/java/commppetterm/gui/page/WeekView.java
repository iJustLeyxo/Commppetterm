package commppetterm.gui.page;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.jetbrains.annotations.NotNull;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public final class WeekView implements Controller {
    @Override
    public @NotNull String path() {
        return "/commppetterm/gui/page/WeekView.fxml";
    }

    @FXML
    private Label monthAndYearLabel;
    @FXML
    private GridPane weekGrid;

    public void initialize() {
        LocalDate today = LocalDate.now();
        updateWeekView(today);
    }

    private void updateWeekView(LocalDate date) {
        // Setzt den Monat und das Jahr
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        monthAndYearLabel.setText(date.format(formatter));

        // Hier könnte man weitere Logik hinzufügen, um die Tage der Woche und Termine anzuzeigen
    }
}
