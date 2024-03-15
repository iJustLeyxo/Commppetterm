package commppetterm.database;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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
        @NotNull LocalDateTime start, @NotNull LocalDateTime end,
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
        return this.start.toLocalTime().equals(LocalTime.of(0 ,0)) && this.end.toLocalTime().equals(LocalTime.of(23, 59));
    }

    /**
     * @return the entry id
     */
    public Long id() { return this.id; }

    /**
     * Determines whether the entry lies on a date
     * @param date The date to test for
     * @return {@code true} if the entry is on the date, otherwise {@code false}
     */
    public boolean on(LocalDate date) {
        return !(this.start.toLocalDate().isAfter(date) || this.end.toLocalDate().isBefore(date));
    }

    /**
     * Returns true if an entry is fully on a date
     * @param date The date to check
     * @return {@code true} if the entry is fully on the date
     */
    public boolean whole(@NotNull LocalDate date) {
        return whole(date, date);
    }

    /**
     * Returns true if an entry is fully in a timeframe
     * @param start The start date to check
     * @param end The end date to check
     * @return {@code true} if the entry is fully on the date
     */
    public boolean whole(@NotNull LocalDate start, @NotNull LocalDate end) {
        return !this.start.toLocalDate().isAfter(start) && this.start.toLocalTime().equals(LocalTime.of(0, 0)) &&
                !this.end.toLocalDate().isBefore(end) && this.end.toLocalTime().equals(LocalTime.of(23, 59));

    }

    /**
     * Returns the start of the event on a specific day
     * @param date The date to get the start time for
     * @return the time on the date where the event starts
     */
    public @Nullable LocalTime start(@NotNull LocalDate date) {
        if (this.start.toLocalDate().isBefore(date)) {
            return LocalTime.of(0, 0);
        } else if (this.start.toLocalDate().isEqual(date)) {
            return this.start.toLocalTime();
        } else {
            return null;
        }
    }

    /**
     * Returns the end of the event on a specific day
     * @param date The date to get the end time for
     * @return the time on the date where the event ends
     */
    public @Nullable LocalTime end(@NotNull LocalDate date) {
        if (this.end.toLocalDate().isAfter(date)) {
            return LocalTime.of(23, 59);
        } else if (this.end.toLocalDate().isEqual(date)) {
            return this.end.toLocalTime();
        } else {
            return null;
        }
    }

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
}
