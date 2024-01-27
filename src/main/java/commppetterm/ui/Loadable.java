package commppetterm.ui;

import org.jetbrains.annotations.NotNull;

public interface Loadable {
    /**
     * Returns the path to the scene of the loadable
     * @return The path
     */
    @NotNull String path();
}
