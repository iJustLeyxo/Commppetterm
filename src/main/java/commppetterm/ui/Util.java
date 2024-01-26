package commppetterm.ui;

import commppetterm.Commppetterm;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * User interface utility functions
 */
public final class Util {
    /**
     * Prepares a stage
     * @param stage The stage to prepare
     * @param scene The scene to show
     */
    public static void prepStage(Stage stage, Scene scene) {
        stage.setTitle(Commppetterm.name);
        stage.setScene(scene);
        // TODO: Set icon
        stage.show();
    }
    
    /**
     * Loads a scene from fxml file
     * @param clazz Class to load from
     * @param file File to load
     */
    public static Scene loadScene(Class clazz, String file) throws IOException {
        FXMLLoader loader = new FXMLLoader(clazz.getResource(file + ".fxml"));
        return new Scene(loader.load(), 320, 240);
    }
}