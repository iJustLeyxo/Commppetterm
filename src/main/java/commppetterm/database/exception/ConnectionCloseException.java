package commppetterm.database.exception;

import org.jetbrains.annotations.NotNull;

/**
 * Exception when closing a connection
 */
public class ConnectionCloseException extends ConnectionException {
    /**
     * Creates a new connection close exception
     * @param cause The cause of the exception
     */
    public ConnectionCloseException(@NotNull Throwable cause) {
        super("Failed to close connection: " + cause.getMessage(), cause);
    }
}
