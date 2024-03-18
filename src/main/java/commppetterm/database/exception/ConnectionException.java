package commppetterm.database.exception;

import org.jetbrains.annotations.NotNull;

/**
 * Connection exception parent class
 */
public abstract class ConnectionException extends DatabaseException {
    /**
     * Creates a new connection exception
     * @param msg   The message of the exception
     * @param cause The cause of the exception
     */
    public ConnectionException(@NotNull String msg, @NotNull Throwable cause) {
        super(msg, cause);
    }
}
