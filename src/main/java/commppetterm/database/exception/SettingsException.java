package commppetterm.database.exception;

import org.jetbrains.annotations.NotNull;

/**
 * Superclass for settings exceptions
 */
public abstract class SettingsException extends Exception {
    /**
     * Creates a new settings exception
     * @param msg The message of the exception
     * @param cause The cause of the exception
     */
    public SettingsException(@NotNull String msg, @NotNull Throwable cause) {
        super(msg, cause);
    }
}
