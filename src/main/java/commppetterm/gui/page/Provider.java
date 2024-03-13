package commppetterm.gui.page;

import javafx.scene.Parent;
import org.jetbrains.annotations.NotNull;

public interface Provider {
    /**
     * Returns the parent of this controller if loaded
     */
    @NotNull Parent parent();
}
