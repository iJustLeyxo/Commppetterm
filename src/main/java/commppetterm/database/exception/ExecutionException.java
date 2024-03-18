package commppetterm.database.exception;

import org.jetbrains.annotations.NotNull;

/**
 * Exception when executing a sql command
 */
public class ExecutionException extends DatabaseException {
    /**
     * Creates a new execution exception
     * @param cause The cause of the exception
     */
    public ExecutionException(@NotNull Throwable cause) {
        super("Failed to execute command: " + cause.getMessage(), cause);
    }
}
