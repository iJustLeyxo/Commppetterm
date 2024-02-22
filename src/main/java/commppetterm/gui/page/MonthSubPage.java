package commppetterm.gui.page;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.Locale;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import org.jetbrains.annotations.NotNull;

public final class MonthSubPage extends SubPage {
    @FXML
    private GridPane grid;

    @Override
    protected void init() {
        this.generate(YearMonth.now());
    }

    private void generate(YearMonth yearMonth) {
        LocalDate date = LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), 1);
        
        String monthName = yearMonth.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault());
        //label.setText(monthName + " " + yearMonth.getYear());
        
        int firstDay = date.getDayOfWeek().getValue() - 2;
        int totalDays = yearMonth.lengthOfMonth();
        
        for (int day = 1; day <= totalDays; day++) {
            Button button = new Button(Integer.toString(day));
            button.getStyleClass().addAll("grid-cell-alt");
            int relativeDay = day + firstDay;
            grid.add(button, (relativeDay % 7) + 1, (relativeDay / 7) + 1);
        }
    }

    @Override
    @NotNull LocalDate getDate() {
        return LocalDate.now();
    }
}