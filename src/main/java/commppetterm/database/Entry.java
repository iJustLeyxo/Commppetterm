package commppetterm.database;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Calendar entry class
 * @param id Entry id
 * @param title Entry title
 * @param info Entry info
 * @param start Entry start
 * @param end Entry end
 * @param recurring Entry recurring profile
 */
public record Entry(
        @Nullable Long id,
        @NotNull String title, @NotNull String info,
        @NotNull LocalDateTime start, @Nullable LocalDateTime end,
        @Nullable Entry.Recurring recurring
) {
    /**
     * @return the entry title
     */
    public String title() { return this.title; }

    /**
     * @return the entry info
     */
    public String info() { return this.info; }

    /**
     * @return the entry start
     */
    public LocalDateTime start() { return this.start; }

    /**
     * @return the entry end
     */
    public LocalDateTime end() { return this.end; }

    /**
     * @return the entry recurring profile
     */
    public Recurring recurring() { return this.recurring; }

    /**
     * @return {@code true} if the entry is timed
     */
    public boolean untimed() {
        return this.end == null || (this.start.getHour() == 0 && this.start.getMinute() == 0 && this.end.getHour() == 0 && this.end.getMinute() == 59);
    }

    /**
     * @return the entry id
     */
    public Long id() { return this.id; }

    /**
     * Repeat profile class
     * @param type Repeat timeframe
     * @param frequency Space between repetitions
     */
    public record Recurring(@NotNull Type type, byte frequency) {
        /**
         * Repeat timeframe
         */
        public enum Type { YEAR, MONTH, WEEK, DAY}

        /**
         * @return type of recurrence
         */
        public Type type() {
            return type;
        }

        /**
         * @return frequency of recurrence
         */
        public byte frequency() {
            return frequency;
        }
    }

    /**
     * Determines whether the entry lies on a date
     * @param date The date to test for
     * @return {@code true} if the entry is on the date, otherwise {@code false}
     */
    public boolean on(LocalDate date) {
        LocalDate startDate = this.start.toLocalDate();

        if (this.end != null) {
            LocalDate endDate = this.end.toLocalDate();

            return (!(startDate.isAfter(date) || endDate.isBefore(date)));
        } else {
            return this.start.toLocalDate().isEqual(date);
        }

        // TODO: Add recurring logic
    }

    /**
     * Returns true if an entry is fully on a date
     * @param date The date to check
     * @return {@code true} if the entry is fully on the date
     */
    public boolean fullOn(@NotNull LocalDate date) {
        return (this.start.toLocalDate().isBefore(date) || (this.start.toLocalDate().isEqual(date) && this.untimed())) &&
                (this.end == null || this.end.toLocalDate().isAfter(date) || (this.end.toLocalDate().isEqual(date) && this.untimed()));

    }
}
