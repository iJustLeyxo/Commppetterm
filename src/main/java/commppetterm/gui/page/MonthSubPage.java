package commppetterm.gui.page;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

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

        /* Generate new contents */
        int firstDay = date.getDayOfWeek().getValue() - 2;
        int totalDays = date.lengthOfMonth();

        for (int day = 1; day <= totalDays; day++) {
            Button button = new Button(Integer.toString(day));
            button.getStyleClass().addAll("grid-cell-alt");
            int relativeDay = day + firstDay;
            this.grid.add(button, (relativeDay % 7) + 1, (relativeDay / 7) + 1);
            this.buttons.add(button);
        }
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