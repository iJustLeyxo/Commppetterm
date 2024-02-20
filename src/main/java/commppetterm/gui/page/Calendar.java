package commppetterm.gui.page;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.Locale;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import org.jetbrains.annotations.NotNull;

public final class Calendar implements Controller{
    @Override
    public @NotNull String path() {
        return "/commppetterm/gui/page/calendar.fxml";
    }

    @FXML
    private Label label;

    @FXML
    private GridPane grid;

    @FXML
    public void initialize() {
        this.generate(YearMonth.now());
    }

    @Override
    public void preInit() {
        // Optional: Code vor dem Laden
    }
    
    @Override
    public void postInit() {
        // Optional: Code nach dem Laden
    }

    private void generate(YearMonth yearMonth) {
        LocalDate date = LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), 1);
        
        String monthName = yearMonth.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault());
        label.setText(monthName + " " + yearMonth.getYear());
        
        int firstDay = date.getDayOfWeek().getValue() - 2;
        int totalDays = yearMonth.lengthOfMonth();
        
        for (int day = 1; day <= totalDays; day++) {
            Button button = new Button(Integer.toString(day));
            button.getStyleClass().addAll("grid-cell-alt");
            int relativeDay = day + firstDay;
            grid.add(button, (relativeDay % 7) + 1, (relativeDay / 7) + 1);
        }
    }   
}