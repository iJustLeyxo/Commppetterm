package commppetterm.gui.page;

// import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.List;

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
}
