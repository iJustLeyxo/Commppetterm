package commppetterm.gui.exception;

import org.jetbrains.annotations.NotNull;

/**
 * Url not found exception when trying to access a nonexistent file
 */
public class URLNotFoundException extends RuntimeException {
    /**
     * Creates a new url not found exception
     * @param object The object from which the url was generated
     * @param resource The name of the resource
     */
    public URLNotFoundException(@NotNull Object object, @NotNull String resource) {
        super("Failed to load resource " + resource + " from " + object.getClass().getCanonicalName());
    }
}
