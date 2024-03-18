package commppetterm.database.exception;

import org.jetbrains.annotations.NotNull;

/**
 * Result set parser exception
 */
public class ParseException extends DatabaseException {
    /**
     * Creates a new parse exception
     * @param cause The cause of the exception
     */
    public ParseException(@NotNull Throwable cause) {
        super("Failed to parse results: " + cause.getMessage(), cause);
    }
}
