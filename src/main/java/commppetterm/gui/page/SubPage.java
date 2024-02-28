package commppetterm.gui.page;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

public abstract class SubPage extends Page {
    /**
     * Date of the subpage timeframe
     */
    protected LocalDate date;

    /**
     * Creates a new subpage
     */
    public SubPage() {
        this.date = LocalDate.now();
    }

    /**
     * Jumps to the previous timeframe of the page
     * @return the text for the label of the parent page
     */
    abstract @NotNull String prev();

    /**
     * Jumps to the next timeframe of the page
     * @return the text for the label of the parent page
     */
    abstract @NotNull String next();
}
