package commppetterm.database;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDateTime;

/**
 * Calendar entry class
 * @param id Entry id
 * @param title Entry title
 * @param info Entry info
 * @param start Entry start
 * @param end Entry end
 * @param repeat Entry repeat profile
 */
public record Entry(@NotNull String title, @NotNull String info,
                    @NotNull LocalDateTime start, @Nullable LocalDateTime end,
                    @Nullable Repeat repeat, Long id) {
    /**
     * Creates a new entry
     * @param title Title text
     * @param info Info Text
     * @param start Start time
     * @param end End time
     * @param repeat Repeat profile
     */
    public Entry(@NotNull String title, @NotNull String info,
                 @NotNull LocalDateTime start, @Nullable LocalDateTime end,
                 @Nullable Repeat repeat, @Nullable Long id) {
        this.title = title;
        this.info = info;
        this.start = start;
        this.end = end;
        this.repeat = repeat;
        this.id = id;
    }

    /**
     * Repeat profile class
     * @param type Repeat timeframe
     * @param space Space between repetitions
     */
    public record Repeat(@NotNull Type type, byte space) {
        /**
         * Creates a new repeat profile
         * @param type  Repeat timeframe
         * @param space Repetition space
         */
        public Repeat(@NotNull Type type, byte space) {
            this.type = type;
            this.space = space;
        }

        /**
         * Repeat timeframe
         */
        public enum Type { YEAR, MONTH, WEEK, DAY; }
    }
}
