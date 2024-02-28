package commppetterm.gui.page;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class SubPage extends Page {
    /**
     * Date of the subpage timeframe
     */
    protected LocalDate date;

    /**
     * Formatter for label texts
     */
    protected DateTimeFormatter formatter;

    /**
     * Creates a new subpage
     */
    public SubPage(DateTimeFormatter formatter) {
        this.formatter = formatter;
        this.date = LocalDate.now();
    }

    /**
     * Retrieves the label text to show in the calendar
     * @return the text for the label
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
}
