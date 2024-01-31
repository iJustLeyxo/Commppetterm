package commppetterm.gui.page;

import org.jetbrains.annotations.NotNull;

public interface Controller {
    /**
     * Returns the path to the scene of the controller
     * @return the path to the scene of the controller
     */
    @NotNull String path();
    
    /**
     * Called before the controller is assigned to the fxml loader
     */
    public default void preInit() {};
    
    /**
     * Called after the controller is assigned to the fxml loader;
     */
    public default void postInit() {};
}
