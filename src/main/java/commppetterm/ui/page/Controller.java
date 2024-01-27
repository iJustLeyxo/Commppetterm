package commppetterm.ui.page;

import org.jetbrains.annotations.NotNull;

public interface Controller {
    /**
     * Returns the path to the scene of the controller
     * @return the path to the scene of the controller
     */
    @NotNull String path();
}
