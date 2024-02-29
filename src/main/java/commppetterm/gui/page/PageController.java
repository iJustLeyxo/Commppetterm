package commppetterm.gui.page;

import commppetterm.App;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.List;
import java.util.Objects;

/**
 * Controller for a page of the calendar
 */
public abstract class PageController extends Controller {
    /**
     * Timeframe of the page
     */
    protected LocalDate date;

    /**
     * Formatter for label texts
     */
    protected DateTimeFormatter formatter;

    /**
     * Creates a new page
     * @param date The date to display
     */
    public PageController(@Nullable LocalDate date) {
        DateTimeFormatterBuilder dtfBuilder = new DateTimeFormatterBuilder();

        for (DtfElement e : this.pattern()) {
            switch (e.type) {
                case LITERAL -> dtfBuilder.appendLiteral(e.contents);
                case PATTERN -> dtfBuilder.appendPattern(e.contents);
            }
        }

        this.formatter = dtfBuilder.toFormatter(App.locale);
        this.date = Objects.requireNonNullElseGet(date, LocalDate::now);
    }

    /**
     * Returns the label text to show in the calendar
     */
    public @NotNull String label() {
        return this.formatter.format(this.date);
    }

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
    abstract @NotNull List<DtfElement> pattern();

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
