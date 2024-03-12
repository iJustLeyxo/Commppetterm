package commppetterm.database;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Database handler
 */
public final class Database {
    public static @NotNull String link = "defaultLink";

    public static @NotNull String user = "defaultUser";

    public static @NotNull String password = "defaultPassword";

    /**
     * Fetches the entries of a day
     * @param date The day to fetch the entries of
     * @return a list of entries
     */
    public static List<Entry> dayEntries(LocalDate date) {
        // TODO: Fetch relevant entries of date, also recurring ones
        return new ArrayList<>();
    }

    /**
     * Fetches the entries of a week
     * @param date The first day of the week to fetch the entries of
     * @return a list of entries
     */
    public static List<Entry> weekEntries(LocalDate date) {
        // TODO: Fetch relevant entries of date, also recurring ones
        return new ArrayList<>();
    }

    /**
     * Creates or edits and entry
     * @param entry The entry to create or edit
     */
    public static void save(@Nullable Entry entry) {
        // TODO: Update or create entry if it doesn't yet exist
    }

    /**
     * Deletes an entry
     * @param entry The entry to delete
     */
    public static void delete(@Nullable Entry entry) {
        // TODO: Delete entry
    }
}
