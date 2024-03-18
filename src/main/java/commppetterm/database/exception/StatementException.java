package commppetterm.database.exception;

import org.jetbrains.annotations.NotNull;

/**
 * Statement exception parent
 */
public abstract class StatementException extends DatabaseException {
    /**
     * Creates a new statement exception
     * @param msg The message of the exception
     * @param cause The cause of the exception
     */
    public StatementException(@NotNull String msg, @NotNull Throwable cause) {
        super(msg, cause);
    }
}
