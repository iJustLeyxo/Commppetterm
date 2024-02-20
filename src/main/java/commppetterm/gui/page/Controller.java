package commppetterm.gui.page;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

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
     * @throws ControllerLoadError In case the controller cannot be loaded
     */
    public @NotNull Parent load() {
        if (loaded) {
            // TODO: Throw error
        }
        loaded = true;
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource(this.getClass().getSimpleName() + ".fxml"));
        loader.setController(this);
        try {
            return loader.load();
        } catch (IOException e) {
            // Todo throw error
        }
    }
}
