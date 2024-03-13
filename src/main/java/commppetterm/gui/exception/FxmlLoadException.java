package commppetterm.gui.exception;

import org.jetbrains.annotations.NotNull;

import java.net.URL;

/**
 * Fxml load exception when an error occurs while loading a fxml file
 */
public class FxmlLoadException extends RuntimeException {
    /**
     * Creates a new fxml load exception
     * @param url Location to the fxml file
     * @param cause Cause of the exception
     */
    public FxmlLoadException(@NotNull URL url, @NotNull Throwable cause) {
        super("Failed to load fxml resource from " + url, cause);
    }
}
