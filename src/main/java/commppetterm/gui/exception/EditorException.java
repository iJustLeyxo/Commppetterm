package commppetterm.gui.exception;

import org.jetbrains.annotations.NotNull;

/**
 * Exception while editing an entry
 */
public class EditorException extends RuntimeException {
    /**
     * Creates a new editor exception
     * @param message The exception message
     */
    public EditorException(@NotNull String message) {
        super(message);
    }
}
