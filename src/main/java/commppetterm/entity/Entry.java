package commppetterm.entity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDateTime;

/**
 * Calendar entry class
 */
public class Entry {
    /**
     * Entry title
     */
    public final @NotNull String title;

    /**
     * Entry info
     */
    public final @NotNull String info;

    /**
     * Entry start
     */
    public final @NotNull LocalDateTime start;

    /**
     * Entry end
     */
    public final @Nullable LocalDateTime end;

    /**
     * Entry repeat profile
     */
    public final @Nullable Repeat repeat;

    /**
     * Creates a new entry
     * @param title Title text
     * @param info Info Text
     * @param start Start time
     * @param end End time
     * @param repeat Repeat profile
     */
    public Entry(@NotNull String title, @NotNull String info, @NotNull LocalDateTime start, @Nullable LocalDateTime end, @Nullable Repeat repeat) {
        this.title = title;
        this.info = info;
        this.start = start;
        this.end = end;
        this.repeat = repeat;
    }

    /**
     * Repeat profile class
     */
    public class Repeat {
        /**
         * Repeat timeframe
         */
        public final @NotNull Type type;

        /**
         * Space between repetitions
         */
        public final byte space;

        /**
         * Creates a new repeat profile
         * @param type Repeat timeframe
         * @param space Repetition space
         */
        public Repeat(@NotNull Type type, byte space) {
            this.type = type;
            this.space = space;
        }

        /**
         * Repeat timeframe
         */
        public enum Type {
            YEAR, MONTH, WEEK, DAY;
        }
    }
}
