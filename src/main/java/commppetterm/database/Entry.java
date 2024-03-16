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
     * @return {@code true} if the entry occurs only once
     */
    public boolean once() { return this.recurring == null; }

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
        date = this.diff(date);

        return !(this.start.toLocalDate().isAfter(date) || this.end.toLocalDate().isBefore(date));
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
        start = this.diff(start);
        end = this.diff(end);

        return !this.start.toLocalDate().isAfter(start) && this.start.toLocalTime().equals(LocalTime.of(0, 0)) &&
                !this.end.toLocalDate().isBefore(end) && this.end.toLocalTime().equals(LocalTime.of(23, 59));

    }

    /**
     * Returns the start of the event on a specific day
     * @param date The date to get the start time for
     * @return the time on the date where the event starts
     */
    public @Nullable LocalTime start(@NotNull LocalDate date) {
        date = this.diff(date);

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
        date = this.diff(date);

        if (this.end.toLocalDate().isAfter(date)) {
            return LocalTime.of(23, 59);
        } else if (this.end.toLocalDate().isEqual(date)) {
            return this.end.toLocalTime();
        } else {
            return null;
        }
    }

    /**
     * Calculates the relative difference between a date and this entry
     * @param date The reference date
     * @return the relative date
     */
    private @NotNull LocalDate diff(@NotNull LocalDate date) {
        if (!this.once()) {
            long diff = 0;
            LocalDate rel;

            switch (this.recurring.type) {
                case DAY -> {
                    diff += Period.between(this.start.toLocalDate(), date).getDays() % recurring.frequency;
                    return this.start.toLocalDate().plusDays(diff);
                }

                case WEEK -> {
                    diff = date.getDayOfWeek().getValue() - this.start.getDayOfWeek().getValue();
                    if (diff < 0) { diff += 7; }
                    diff += (Period.between(this.start.toLocalDate().plusDays(diff), date).getDays() / 7) % recurring.frequency * 7;
                    return this.start.toLocalDate().plusDays(diff);
                }

                case MONTH -> {
                    diff = date.getDayOfMonth() - this.start.getDayOfMonth();
                    if (diff < 0) { diff += date.lengthOfMonth(); }
                    diff %= this.start.toLocalDate().lengthOfMonth();
                    rel = this.start.toLocalDate().plusDays(diff);
                    return rel.plusMonths(Period.between(rel, date).getMonths() % recurring.frequency);
                }

                case YEAR -> {
                    diff = date.getDayOfYear() - this.start.getDayOfYear();
                    if (diff < 0) { diff += date.lengthOfYear(); }
                    diff %= this.start.toLocalDate().lengthOfYear();
                    rel = this.start.toLocalDate().plusDays(diff);
                    return rel.plusYears(Period.between(rel, date).getYears() % recurring.frequency);
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
