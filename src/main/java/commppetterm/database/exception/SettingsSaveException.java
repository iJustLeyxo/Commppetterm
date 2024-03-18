package commppetterm.database.exception;

import org.jetbrains.annotations.NotNull;

import java.io.File;

/**
 * Settings save exception
 */
public class SettingsSaveException extends SettingsException {
    /**
     * Creates a new settings save exception
     * @param file The file that was used to try loading the settings
     * @param cause The cause of the exception
     */
    public SettingsSaveException(@NotNull File file, @NotNull Throwable cause) {
        super("Failed to save the settings to " + file.getName() + ": " + cause.getClass().getSimpleName() + ": " + cause.getMessage(), cause);
    }
}
