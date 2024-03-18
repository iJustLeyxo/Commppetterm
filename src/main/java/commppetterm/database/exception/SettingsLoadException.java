package commppetterm.database.exception;

import org.jetbrains.annotations.NotNull;

import java.io.File;

/**
 * Settings load exception
 */
public class SettingsLoadException extends SettingsException {
    /**
     * Creates a new settings load exception
     * @param file The file that was used to try loading the settings
     * @param cause The cause of the exception
     */
    public SettingsLoadException(@NotNull File file, @NotNull Throwable cause) {
        super("Failed to load the settings from " + file.getName() + ": " + cause.getClass().getSimpleName() + ": " + cause.getMessage(), cause);
    }
}
