package commppetterm.gui.page;

import javafx.scene.Parent;
import org.jetbrains.annotations.NotNull;

/**
 * Provider interface for classes that provide a parent object
 */
public interface Provider {
    /**
     * Returns the parent of this controller if loaded
     * @return a parent object
     */
    @NotNull Parent parent();
}
