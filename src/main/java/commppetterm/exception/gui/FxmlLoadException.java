package commppetterm.exception.gui;

import org.jetbrains.annotations.NotNull;

import java.net.URL;

public class FxmlLoadException extends Exception {
    public FxmlLoadException(@NotNull URL url, @NotNull Throwable cause) {
        super("Failed to load fxml resource from " + url.toString(), cause);
    }
}
