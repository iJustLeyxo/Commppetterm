package commppetterm.gui.page;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.List;

import commppetterm.entity.Entry;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import org.jetbrains.annotations.NotNull;

import commppetterm.App;

/**
 * Controller for a page of the calendar
 */
public abstract class PageController extends Controller {
    /**
     * Formatter for label texts
     */
    protected DateTimeFormatter formatter;

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

        this.formatter = dtfBuilder.toFormatter(App.locale);
    }

    /**
     * Returns the label text to show in the calendar
     */
    public @NotNull String label() {
        return this.formatter.format(App.date);
    }

    /**
     * Generates the contents of the page
     */
    protected void generate() {};

    /**
     * Jumps to the previous timeframe of the page
     */
    abstract void prev();

    /**
     * Jumps to the next timeframe of the page
     */
    abstract void next();

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
        public enum Type {
            LITERAL, PATTERN;
        }
    }

    /**
     * Controller for cells
     */
    public abstract static class CellController extends Controller {
        /**
         * Cell button
         */
        protected final @NotNull Button button;

        /**
         * Creates a new cell controller
         */
        public CellController(@NotNull Button button) {
            this.button = button;
        }

        @Override
        public @NotNull Parent load() {
            return this.button;
        }
    }

    /**
     * Controller for day cells
     */
    public static class EntryController extends CellController {
        /**
         * Associated entry
         */
        private @NotNull final Entry entry;

        /**
         * Creates a new day cell controller
         * @param entry The associated entry
         */
        public EntryController(@NotNull Entry entry) {
            super(new Button(entry.title));
            this.entry = entry;

            this.button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    App.entry = entry;
                }
            });
        }
    }
}
