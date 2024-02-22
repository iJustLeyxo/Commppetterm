package commppetterm.gui.page;

import commppetterm.exception.gui.FxmlLoadException;
import commppetterm.exception.gui.ControllerLoadedException;
import commppetterm.exception.gui.URLNotFoundException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URL;

/**
 * Scene controller superclass
 */
public abstract class Controller {
    /**
     * Whether the controller has already been loaded
     */
    private boolean loaded = false;

    /**
     * Loads the controller's main resource
     * @return a parent object
     */
    public @NotNull Parent load() throws Exception {
        if (loaded) {
            throw new ControllerLoadedException(this.getClass().getName());
        }
        loaded = true;
        URL url = this.getClass().getResource(this.getClass().getSimpleName() + ".fxml");
        FXMLLoader loader = new FXMLLoader(url);
        loader.setController(this);
        try {
            return loader.load();
        } catch (IOException e) {
            if (url == null) {
                throw new URLNotFoundException(this.getClass(), this.getClass().getSimpleName());
            }
            throw new FxmlLoadException(url, e);
        }
    }
}
