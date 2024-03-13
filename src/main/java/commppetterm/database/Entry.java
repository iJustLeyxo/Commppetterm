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
        @NotNull String title, @NotNull String info,
        @NotNull LocalDateTime start, @Nullable LocalDateTime end,
        @Nullable Entry.Recurrence recurring, Long id
) {
    /**
     * Creates a new entry
     * @param title Title text
     * @param info Info Text
     * @param start Start time
     * @param end End time
     * @param recurring Recurrence profile
     */
    public Entry(@NotNull String title, @NotNull String info,
                 @NotNull LocalDateTime start, @Nullable LocalDateTime end,
                 @Nullable Entry.Recurrence recurring, @Nullable Long id) {
        this.title = title;
        this.info = info;
        this.start = start;
        this.end = end;
        this.recurring = recurring;
        this.id = id;
    }

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
    public Recurrence recurring() { return this.recurring; }

    /**
     * @return the entry id
     */
    public Long id() { return this.id; }

    /**
     * Repeat profile class
     * @param type Repeat timeframe
     * @param frequency Space between repetitions
     */
    public record Recurrence(@NotNull Type type, byte frequency) {
        /**
         * Creates a new recurring profile
         * @param type  Repeat timeframe
         * @param frequency Repetition frequency
         */
        public Recurrence(@NotNull Type type, byte frequency) {
            this.type = type;
            this.frequency = frequency;
        }

        /**
         * Repeat timeframe
         */
        public enum Type { YEAR, MONTH, WEEK, DAY; }

        /**
         * Methode zur Rückgabe des Typs der Wiederholung
         *
         * @return der Typ der Wiederholung
         */
        public Type getType() {
            return type;
        }

        /**
         * Methode zur Rückgabe der Frequenz
         *
         * @return die Frequenz
         */
        public byte getFrequency() {
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
}
