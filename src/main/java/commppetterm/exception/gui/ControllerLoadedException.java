package commppetterm.exception.gui;

import org.jetbrains.annotations.NotNull;

public class ControllerLoadedException extends Exception {
    public ControllerLoadedException(@NotNull String controller) {
        super("Controller " + controller + " has already been loaded");
    }
}
