package commppetterm.gui.page;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.Locale;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;


public class Calendar implements Controller{

    @Override
    public String path() {
        return "/commppetterm/gui/page/calendar.fxml";
    }

    @FXML
    private Label monthLabel;
    @FXML
    private GridPane calendarGrid;

    @FXML
    public void initialize() {
        fillCalendar(YearMonth.now()); // Füllt den Kalender mit Tagen
    }

    @Override
    public void preInit() {
        // Optional: Code vor dem Laden
    }
    
    @Override
    public void postInit() {
        // Optional: Code nach dem Laden
    }

    private void fillCalendar(YearMonth yearMonth) {
        LocalDate calendarDate = LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), 1);
        
        String monthName = yearMonth.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault());
        monthLabel.setText(monthName + " " + yearMonth.getYear());
        
        int dayOfWeekOfFirst = calendarDate.getDayOfWeek().getValue();
        int daysInMonth = yearMonth.lengthOfMonth();
        
        for (int i = 0, dayOfMonth = 1; dayOfMonth <= daysInMonth; i++) {
            int col = (i + dayOfWeekOfFirst - 1) % 7;
            int row = (i + dayOfWeekOfFirst - 1) / 7;
            
            Label dayLabel = new Label(Integer.toString(dayOfMonth));
            dayLabel.getStyleClass().addAll("day-cell");
            calendarGrid.add(dayLabel, col + 1, row + 1); // +1 um unter den Wochentag-Headern zu beginnen
    
            dayOfMonth++;
        }
    }   
}