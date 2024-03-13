package commppetterm.gui.exception;

import org.jetbrains.annotations.NotNull;

import java.net.URL;

public class FxmlLoadException extends RuntimeException {
    public FxmlLoadException(@NotNull URL url, @NotNull Throwable cause) {
        super("Failed to load fxml resource from " + url, cause);
    }
}
