package commppetterm.gui.page;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.LinkedList;
import java.util.List;

import commppetterm.database.Entry;
import commppetterm.gui.App;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import org.jetbrains.annotations.NotNull;

/**
 * Controller for a page of the calendar
 */
public abstract class PageController extends Controller {
    /**
     * Formatter for label texts
     */
    protected final DateTimeFormatter formatter;

    /**
     * List of all contents
     */
    protected final LinkedList<Parent> contents;

    /**
     * Creates a new page
     */
    public PageController() {
        DateTimeFormatterBuilder dtfBuilder = new DateTimeFormatterBuilder();

        for (DtfElement e : this.formatting()) {
            switch (e.type) {
                case LITERAL -> dtfBuilder.appendLiteral(e.contents);
                case PATTERN -> dtfBuilder.appendPattern(e.contents);
            }
        }

        this.formatter = dtfBuilder.toFormatter(App.get().LOCALE);
        this.contents = new LinkedList<>();
    }

    /**
     * Returns the label text to show in the calendar
     * @return the label text to be shown in the calendar
     */
    public @NotNull String label() {
        return this.formatter.format(App.get().date());
    }

    /**
     * Jumps to the previous timeframe of the page
     */
    abstract @NotNull LocalDate prev(@NotNull LocalDate date);

    /**
     * Jumps to the next timeframe of the page
     */
    abstract @NotNull LocalDate next(@NotNull LocalDate date);

    /**
     * Returns the pattern to use for date formatting
     */
    abstract @NotNull List<DtfElement> formatting();

    /**
     * Record for date time formatter elements
     * @param type Element type
     * @param contents Element contents
     */
    public record DtfElement(@NotNull Type type, @NotNull String contents) {
        /**
         * Date time formatter element type
         */
        public enum Type {
            /**
             * Literal element type
             */
            LITERAL,

            /**
             * Pattern element type
             */
            PATTERN
        }
    }

    /**
     * Controller for cells
     */
    public abstract static class CellController implements Provider {
        /**
         * Cell button
         */
        protected final @NotNull Button element;

        /**
         * Creates a new cell controller
         * @param element the button to create a cell controller of
         */
        public CellController(@NotNull Button element) {
            this.element = element;
        }

        @Override
        public @NotNull Parent parent() {
            return this.element;
        }
    }

    /**
     * Controller for day cells
     */
    public static class EntryController extends CellController {
        /**
         * Creates a new day cell controller
         * @param entry The associated entry
         */
        public EntryController(@NotNull Entry entry) {
            super(new Button(entry.title()));

            this.element.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            this.element.getStyleClass().add(".small-cell-height");

            this.element.setOnAction(actionEvent -> {
                App.get().entry(entry);
                assert App.get().provider() != null;
                ((Calendar) App.get().provider()).enableEdit();
            });
        }
    }
}
