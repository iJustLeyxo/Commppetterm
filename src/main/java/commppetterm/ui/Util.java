package commppetterm.ui;

import com.gluonhq.charm.glisten.visual.Swatch;
import com.gluonhq.charm.glisten.visual.Theme;
import commppetterm.Commppetterm;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * User interface utility functions
 */
public final class Util {
    /**
     * Loads a scene from fxml file
     * @param file File to load scene from
     */
    public static Scene load(@NotNull String file) throws IOException {
        FXMLLoader loader = new FXMLLoader(Interface.class.getResource(file + ".fxml"));
        return loader.load();
    }
    
    /**
     * Prepares a stage
     * @param stage The stage to prepare
     * @param file The file to load scene from
     */
    public static void prepare(@NotNull Stage stage, @NotNull String file) throws IOException {
        stage.setScene(Util.load(file));
        stage.setTitle(Commppetterm.name);
        // TODO: Set icon
        stage.show();
    }
}