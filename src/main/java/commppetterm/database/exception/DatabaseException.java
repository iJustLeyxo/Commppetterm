package commppetterm.database.exception;

import org.jetbrains.annotations.NotNull;

/**
 * Database exception parent
 */
public abstract class DatabaseException extends Exception {
    /**
     * Creates a new database exception
     * @param msg The message of the exception
     * @param cause The cause of the exception
     */
    public DatabaseException(@NotNull String msg, @NotNull Throwable cause) {
        super(msg, cause);
    }
}
