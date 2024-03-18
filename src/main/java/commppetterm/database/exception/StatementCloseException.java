package commppetterm.database.exception;

import org.jetbrains.annotations.NotNull;

/**
 * Exception when closing a statement
 */
public class StatementCloseException extends StatementException {
    /**
     * Creates a new settings exception
     * @param cause The cause of the exception
     */
    public StatementCloseException(@NotNull Throwable cause) {
        super("Failed to close statement: " + cause.getMessage(), cause);
    }
}
