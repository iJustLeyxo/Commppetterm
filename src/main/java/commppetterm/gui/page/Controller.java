package commppetterm.gui.page;

import commppetterm.gui.exception.FxmlLoadException;
import commppetterm.gui.exception.ControllerLoadedException;
import commppetterm.gui.exception.URLNotFoundException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.net.URL;

/**
 * Controller superclass
 */
public abstract class Controller {
    /**
     * Loaded parent
     */
    private @Nullable Parent parent;

    /**
     * Creates a new controller
     */
    public Controller() {
        this.parent = null;
    }

    /**
     * Loads the controller's resource
     * @return a parent object
     */
    public @NotNull Parent load() throws ControllerLoadedException, FxmlLoadException, URLNotFoundException {
        if (this.parent != null) {
            throw new ControllerLoadedException(this.getClass().getName());
        }

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

        this.init();
        assert this.parent != null;
        return this.parent;
    }

    /**
     * Initialises the controller
     */
    protected void init() throws ControllerLoadedException, FxmlLoadException, URLNotFoundException {};

    /**
     * Returns the parent of this controller if loaded
     */
    public @Nullable Parent parent() { return this.parent; }
}
