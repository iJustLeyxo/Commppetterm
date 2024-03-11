package commppetterm.gui.exception;

import org.jetbrains.annotations.NotNull;

public class URLNotFoundException extends Exception {
    public URLNotFoundException(@NotNull Class clazz, @NotNull String resource) {
        super("Failed to load resource " + resource + " from " + clazz.getCanonicalName());
    }
}
