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
    public Controller() {
        URL url = this.getClass().getResource(this.getClass().getSimpleName() + ".fxml");
        FXMLLoader loader = new FXMLLoader(url);
        loader.setController(this);

        if (url == null) {
            throw new URLNotFoundException(this.getClass(), this.getClass().getSimpleName());
        }

        try {
            this.parent = loader.load();
        } catch (IOException e) {
            throw new FxmlLoadException(url, e);
        }
    }

    @Override
    public @NotNull Parent parent() { return this.parent; }

    /**
     * Reloads the controller
     */
    protected void reload() {}
}
