package commppetterm.gui.page;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.LinkedList;
import java.util.List;

import commppetterm.database.Entry;
import commppetterm.gui.App;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import org.jetbrains.annotations.NotNull;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
 

/**
 * Page for showing weeks
 */
public final class WeekPage extends PageController {
    @FXML
    private GridPane entries;

    @Override
    protected void init() {
        this.reload();
    }

    @Override
    @NotNull LocalDate prev(@NotNull LocalDate date) {
        return date.minusWeeks(1);
    }

    @Override
    @NotNull LocalDate next(@NotNull LocalDate date) {
        return date.plusWeeks(1);
    }

    @Override
    @NotNull List<DtfElement> formatting() {
        return List.of(
                new DtfElement(DtfElement.Type.PATTERN, "w"),
                new DtfElement(DtfElement.Type.LITERAL, ". KW "),
                new DtfElement(DtfElement.Type.PATTERN, "yyyy")
        );
    }

    @Override
    protected void reload() {
        /* Clear grid */
        this.entries.getChildren().removeAll(contents);
        this.contents.clear();

        /* Generate */
        LocalDate iter = App.get().date().minusDays(App.get().date().getDayOfWeek().getValue() - 1);
        Parent parent;
        int colStep = 1;
        int colSpan = 0;
        int rowOffset = 2;
        int rowStart, rowSpan;

        do {
            /* Generate entries */
            for (Entry entry : App.get().database().dayEntries(iter)) {
                if (entry.on(iter)) {
                    if (entry.end() != null) {
                        if (entry.start().toLocalDate().isBefore(iter)) {
                            rowStart = 0;
                        } else {
                            rowStart = entry.start().getHour() + entry.start().getMinute() % 30;
                        }

                        if (entry.end().toLocalDate().isAfter(iter)) {
                            rowSpan = 24 - rowStart;
                        } else {
                            rowSpan = entry.end().getHour() + entry.end().getMinute() % 30 - rowStart;
                        }
                    } else {
                        rowStart = 31;
                        rowSpan = 24;
                    }

                    rowStart += rowOffset;

                    parent = new EntryController(entry).load();
                    this.contents.add(parent);
                    this.entries.add(parent, colStep + colSpan, rowStart, 1, rowSpan);
                    colSpan++;
                }
            }

            // TODO: Detect full week events

            if (colSpan == 0) { colSpan = 1; }

            /* Generate day cell */
            parent = new DayCellController(iter).load();
            this.contents.add(parent);
            this.entries.add(parent, colStep, 0, colSpan, 1);

            /* Increment iterators */
            colStep = colStep + colSpan;
            colSpan = 0;
            iter = iter.plusDays(1);
        } while (iter.getDayOfWeek().getValue() != 1);
    }

    /**
     * Controller for day cells
     */
    public static class DayCellController extends CellController {
        /**
         * Creates a new day cell controller
         * @param date The associated date
         */
        public DayCellController(@NotNull LocalDate date) {
            super(new Button(date.getDayOfWeek().getDisplayName(TextStyle.SHORT, App.get().LOCALE) + "\n" + date.getDayOfMonth()));

            this.element.setMaxSize(Double.MAX_VALUE, 50);

            if (date.equals(LocalDate.now())) {
                this.element.getStyleClass().addAll("cell");
            } else  {
                this.element.getStyleClass().addAll("cell", "alt-color");
            }

            this.element.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    try {
                        App.get().date(date);
                        Calendar.get().swap(new DayPage());
                    } catch (Exception e) {
                        App.get().LOGGER.severe(e.toString());
                        e.printStackTrace(System.out);
                    }
                }
            });
        }
    }
}
