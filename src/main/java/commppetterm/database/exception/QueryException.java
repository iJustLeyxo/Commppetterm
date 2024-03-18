package commppetterm.database.exception;

import org.jetbrains.annotations.NotNull;

public class QueryException extends DatabaseException {
    /**
     * Creates a new database exception
     * @param cause The cause of the exception
     */
    public QueryException(@NotNull Throwable cause) {
        super("Failed to execute query: " + cause.getMessage(), cause);
    }
}
