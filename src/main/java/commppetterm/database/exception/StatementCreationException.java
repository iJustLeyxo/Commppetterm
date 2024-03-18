package commppetterm.database.exception;

import org.jetbrains.annotations.NotNull;

/**
 * Exception when creating a statement
 */
public class StatementCreationException extends StatementException {
    /**
     * Creates a new settings exception
     * @param cause The cause of the exception
     */
    public StatementCreationException(@NotNull Throwable cause) {
        super("Failed to create statement: " + cause.getMessage(), cause);
    }
}
