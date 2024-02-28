package commppetterm.gui.page;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.WeekFields;
import java.util.LinkedList;
import java.util.Locale;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import org.jetbrains.annotations.NotNull;

/**
 * Subpage for showing months
 */
public final class MonthSubPage extends SubPage {
    @FXML
    private GridPane grid;

    /**
     * List of all visible buttons in the grid
     */
    private LinkedList<Button> buttons;

    /**
     * Creates a new month subpage
     */
    public MonthSubPage() {
        super(DateTimeFormatter.ofPattern("MMM yyyy"));
    }

    @Override
    protected void init() {
        this.buttons = new LinkedList<>();
        this.date = LocalDate.now();
        this.reload();
    }
    
    private void reload() {
        /* Clear grid pane */
        this.grid.getChildren().removeAll(buttons);
        this.buttons = new LinkedList<>();

        /* Generate days */
        LocalDate firstDayOfMonth = LocalDate.of(this.date.getYear(), this.date.getMonth(), 1);
        int firstDayOfWeek = firstDayOfMonth.getDayOfWeek().getValue();
        int daysInMonth = this.date.lengthOfMonth();

        for (int dayOfMonth = 1; dayOfMonth <= daysInMonth; dayOfMonth++) {
            int dayOfWeek = (dayOfMonth + firstDayOfWeek - 2) % 7 + 1;
            int weekOfMonth = (dayOfMonth + firstDayOfWeek - 2) / 7 + 1;

            Button button = new Button(Integer.toString(dayOfMonth));
            button.getStyleClass().addAll("grid-cell-alt");
            this.buttons.add(button);
            this.grid.add(button, dayOfWeek, weekOfMonth);
        }

        /* Generate weeks */
        int firstWeekOfMonth = firstDayOfMonth.get(WeekFields.ISO.weekOfYear());
        int weeksInMonth = (firstDayOfWeek + daysInMonth + 5) / 7;

        for (int weekOfMonth = 1; weekOfMonth <= weeksInMonth; weekOfMonth++) {
            Button button = new Button(Integer.toString(weekOfMonth + firstWeekOfMonth - 1));
            button.getStyleClass().addAll("grid-cell");
            this.buttons.add(button);
            this.grid.add(button, 0, weekOfMonth);
        }

        //TODO: Make all buttons functional
    }

    @Override
    void prev() {
        this.date = this.date.minusMonths(1);
        this.reload();
    }

    @Override
    void next() {
        this.date = this.date.plusMonths(1);
        this.reload();
    }
}