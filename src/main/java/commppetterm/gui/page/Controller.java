package commppetterm.gui.page;

import java.io.IOException;
import java.net.URL;

import org.jetbrains.annotations.NotNull;

import commppetterm.gui.exception.FxmlLoadException;
import commppetterm.gui.exception.URLNotFoundException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

/**
 * Controller superclass
 */
public abstract class Controller implements Provider {
    /**
     * Loaded parent
     */
    private final @NotNull Parent parent;

    /**
     * Creates a new controller
     */
    public Controller() throws URLNotFoundException, FxmlLoadException {
        URL url = this.getClass().getResource(this.getClass().getSimpleName() + ".fxml");
        FXMLLoader loader = new FXMLLoader(url);
        loader.setController(this);

        try {
            this.parent = loader.load();
        } catch (IOException e) {
            if (url == null) {
                throw new URLNotFoundException(this.getClass(), this.getClass().getSimpleName());
            }
            throw new FxmlLoadException(url, e);
        }
    }

    /**
     * Returns the parent of this controller if loaded
     */
    public @NotNull Parent parent() { return this.parent; }

    /**
     * Reloads the controller
     */
    protected void reload() {}
}
