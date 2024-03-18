package commppetterm.database.exception;

import org.jetbrains.annotations.NotNull;

/**
 * Result set closing exception
 */
public class ResultSetCloseException extends DatabaseException {
    /**
     * Creates a new result set close exception
     * @param cause The cause of the exception
     */
    public ResultSetCloseException(@NotNull Throwable cause) {
        super("Failed to close result set: " + cause.getMessage(), cause);
    }
}
