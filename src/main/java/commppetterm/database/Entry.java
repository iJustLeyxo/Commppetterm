package commppetterm.database;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;

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
     * Gets the entry title
     * @return the entry title
     */
    public @NotNull String title() { return this.title; }

    /**
     * Gets the entry info
     * @return the entry info
     */
    public @NotNull String info() { return this.info; }

    /**
     * Gets the entry start point
     * @return the entry start
     */
    public @NotNull LocalDateTime start() { return this.start; }

    /**
     * Gets the entry end point
     * @return the entry end
     */
    public @NotNull LocalDateTime end() { return this.end; }

    /**
     * Gets the entry recurrence profile
     * @return the entry recurring profile
     */
    public @Nullable Recurring recurring() { return this.recurring; }

    /**
     * Gets whether the entry is recurring
     * @return {@code true} if the entry reoccurs
     */
    public boolean recurs() { return this.recurring != null; }

    /**
     * Gets whether the entry is timed
     * @return {@code true} if the entry is timed
     */
    public boolean untimed() {
        return this.start.toLocalTime().equals(LocalTime.of(0 ,0)) && this.end.toLocalTime().equals(LocalTime.of(23, 59));
    }

    /**
     * Gets the entry id
     * @return the entry id
     */
    public Long id() { return this.id; }

    /**
     * Determines whether the entry lies on a date
     * @param date The date to test for
     * @return {@code true} if the entry is on the date, otherwise {@code false}
     */
    public boolean on(LocalDate date) {
        date = this.relative(date);

        return !(this.start.toLocalDate().isAfter(date) || this.end.toLocalDate().isBefore(date));
    }

    /**
     * Determines whether the entry lies in a time span
     * @param start The first date to test for
     * @param end The last date to test for
     * @return {@code true} if the entry is on the date, otherwise {@code false}
     */
    public boolean on(@NotNull LocalDate start, @NotNull LocalDate end) {
        if (this.recurs()) {
            Period per = Period.between(start, end);

            switch (this.recurring.type) {
                case DAY -> { if (per.getDays() > 0) { return true; } }
                case WEEK -> { if (per.getDays() > 6) { return true; } }
                case MONTH -> { if (per.getMonths() > 0) { return true; } }
                case YEAR -> { if (per.getYears() > 0) { return true; } }
            }
        }

        Period per = Period.between(start, end);
        start = this.relative(start);
        end = this.relative(end);

        if (end.isBefore(start)) {
            return true;
        } else {
            return !(this.start.toLocalDate().isAfter(end) || this.end.toLocalDate().isBefore(start));
        }
    }

    /**
     * Returns true if an entry is fully on a date
     * @param date The date to check
     * @return {@code true} if the entry is fully on the date
     */
    public boolean whole(@NotNull LocalDate date) {
        return whole(date, date); /* Logic is managed by whole(start, end) method */
    }

    /**
     * Returns true if an entry is fully in a timeframe
     * @param start The start date to check
     * @param end The end date to check
     * @return {@code true} if the entry is fully on the date
     */
    public boolean whole(@NotNull LocalDate start, @NotNull LocalDate end) {
        start = this.relative(start);
        end = this.relative(end);

        return !this.start.toLocalDate().isAfter(start) && this.start.toLocalTime().equals(LocalTime.of(0, 0)) &&
                !this.end.toLocalDate().isBefore(end) && this.end.toLocalTime().equals(LocalTime.of(23, 59));

    }

    /**
     * Returns the start of the event on a specific day
     * @param date The date to get the start time for
     * @return the time on the date where the event starts
     */
    public @NotNull LocalTime start(@NotNull LocalDate date) {
        date = this.relative(date);

        if (this.start.toLocalDate().isBefore(date)) {
            return LocalTime.of(0, 0);
        } else if (this.start.toLocalDate().isEqual(date)) {
            return this.start.toLocalTime();
        } else {
            return LocalTime.of(0, 0);
        }
    }

    /**
     * Returns the end of the event on a specific day
     * @param date The date to get the end time for
     * @return the time on the date where the event ends
     */
    public @NotNull LocalTime end(@NotNull LocalDate date) {
        date = this.relative(date);

        if (this.end.toLocalDate().isAfter(date)) {
            return LocalTime.of(23, 59);
        } else if (this.end.toLocalDate().isEqual(date)) {
            return this.end.toLocalTime();
        } else {
            return LocalTime.of(23, 59);
        }
    }

    /**
     * Calculates the relative difference between a date and this entry
     * @param date The reference date
     * @return the relative date
     */
    private @NotNull LocalDate relative(@NotNull LocalDate date) {
        if (this.recurs()) {
            long sam = 0;
            LocalDate rel;

            switch (this.recurring.type) {
                case DAY -> {
                    sam += Period.between(this.start.toLocalDate(), date).getDays() % recurring.frequency;
                    return this.start.toLocalDate().plusDays(sam);
                }

                case WEEK -> {
                    sam = date.getDayOfWeek().getValue() - this.start.getDayOfWeek().getValue();
                    if (sam < 0) { sam += 7; }
                    sam += ((Period.between(this.start.toLocalDate().plusDays(sam), date).getDays() + 1) / 7) % recurring.frequency;
                    return this.start.toLocalDate().plusDays(sam);
                }

                case MONTH -> {
                    sam = date.getDayOfMonth() - this.start.getDayOfMonth();
                    if (sam < 0) { sam += date.lengthOfMonth(); }
                    sam %= this.start.toLocalDate().lengthOfMonth();
                    rel = this.start.toLocalDate().plusDays(sam);
                    return rel.plusMonths(Period.between(rel, date).getMonths() % recurring.frequency);
                }

                case YEAR -> {
                    /* Year */
                    sam = date.getMonthValue() - this.start.getMonthValue();
                    if (sam < 0) { sam += 12; }
                    sam += (Period.between(this.start.toLocalDate().plusMonths(sam), date).getMonths() / 12) % recurring.frequency;
                    rel = this.start.toLocalDate().plusMonths(sam);

                    /* Month */
                    sam = date.getDayOfMonth() - this.start.getDayOfMonth();
                    if (sam < 0) { sam += rel.lengthOfMonth(); }
                    sam %= rel.lengthOfMonth();
                    return rel.plusDays(sam);
                }
            }
        }

        return date;
    }

    /**
     * Repeat profile class
     * @param type Repeat timeframe
     * @param frequency Space between repetitions
     */
    public record Recurring(@NotNull Entry.Recurring.RecurringType type, byte frequency) {
        /**
         * Repeat timeframe
         */
        public enum RecurringType {
            /**
             * Yearly recurrence
             */
            YEAR,

            /**
             * Monthly recurrence
             */
            MONTH,

            /**
             * Weekly recurrence
             */
            WEEK,

            /**
             * Daily recurrence
             */
            DAY
        }

        /**
         * Gets the type of recurrence
         * @return type of recurrence
         */
        public RecurringType type() {
            return type;
        }

        /**
         * Gets the frequency of recurrence
         * @return frequency of recurrence
         */
        public byte frequency() {
            return frequency;
        }
    }
}
