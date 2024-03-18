package commppetterm.database.exception;

import org.jetbrains.annotations.NotNull;

/**
 * Database connect exception
 */
public class ConnectException extends ConnectionException {
    /**
     * Creates a new settings exception
     * @param cause The cause of the exception
     */
    public ConnectException(@NotNull Throwable cause) {
        super("Failed to connect to database: " + cause.getClass().getSimpleName() + ": " + cause.getMessage(), cause);
    }
}
