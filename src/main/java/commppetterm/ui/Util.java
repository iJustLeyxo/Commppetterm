package commppetterm.ui;

import com.gluonhq.charm.glisten.visual.Swatch;
import com.gluonhq.charm.glisten.visual.Theme;
import commppetterm.Commppetterm;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * User interface utility functions
 */
public final class Util {
    /**
     * Loads a scene from fxml file
     * @param loadable The class to load the scene from
     */
    public static Scene load(@NotNull Loadable loadable) throws IOException {
        FXMLLoader loader = new FXMLLoader(loadable.getClass().getResource(loadable.path()));
        return loader.load();
    }
    
    /**
     * Prepares a stage
     * @param stage The stage to prepare
     * @param loadable The class to load the scene from
     */
    public static void prepare(@NotNull Stage stage, @NotNull Loadable loadable) throws IOException {
        stage.setScene(Util.load(loadable));
        stage.setTitle(Commppetterm.name);
        Image icon = new Image(Interface.class.getResource("icon.png").toString());
        stage.getIcons().add(icon);
        stage.show();
    }
}