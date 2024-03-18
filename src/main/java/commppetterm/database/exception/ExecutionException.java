package commppetterm.database.exception;

import org.jetbrains.annotations.NotNull;

public class ExecutionException extends DatabaseException {
    /**
     * Creates a new execution exception
     * @param msg The message of the exception
     * @param cause The cause of the exception
     */
    public ExecutionException(@NotNull Throwable cause) {
        super("Failed to execute command: " + cause.getMessage(), cause);
    }
}
