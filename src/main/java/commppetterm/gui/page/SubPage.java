package commppetterm.gui.page;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

public abstract class SubPage extends Page {
    /**
     * Retrieves the current time of the subpage
     * @return the current time of the subpage
     */
    abstract @NotNull LocalDate getDate();
}
