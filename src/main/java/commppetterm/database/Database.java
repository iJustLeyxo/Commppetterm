package commppetterm.database;

import commppetterm.entity.Entry;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Database handler
 */
public final class Database {
    /**
     * Fetches the entries of a day
     * @param date The day to fetch the entries of
     * @return a list of entries
     */
    public static List<Entry> entries(LocalDate date) {
        // TODO: Fetch entries of date
        return new ArrayList<>();
    }

    /**
     * Creates or edits and entry
     * @param entry The entry to create or edit
     */
    public static void save(@NotNull Entry entry) {
        // TODO: Update or create entry if it doesn't yet exist
    }

    /**
     * Deletes an entry
     * @param entry The entry to delete
     */
    public static void delete(@NotNull Entry entry) {
        // TODO: Delete entry
    }
}
