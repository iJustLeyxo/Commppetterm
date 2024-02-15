package commppetterm.util;
import java.io.IOException;

import org.jetbrains.annotations.NotNull;

import commppetterm.Commppetterm;
import commppetterm.gui.Gui;
import commppetterm.gui.page.Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * User interface utility functions
 */
public final class GuiTools {
    /**
     * Loads a scene from fxml file
     * @param controller The class to load the scene from
     */
    public static Parent load(@NotNull Controller controller) throws IOException {
        FXMLLoader loader = new FXMLLoader(controller.getClass().getResource(controller.path()));
        controller.preInit();
        loader.setController(controller);
        Parent parent = loader.load();
        controller.postInit();
        return parent;
    }
    
    /**
     * Prepares a stage
     * @param stage The stage to prepare
     * @param controller The class to load the scene from
     */
    public static void prepare(@NotNull Stage stage, @NotNull Controller controller) throws IOException {
        stage.setScene(new Scene(GuiTools.load(controller)));
        stage.setTitle(Commppetterm.name);
        // Image icon = new Image(Gui.class.getResource("icon.png").toString());
        // Image icon = new Image(Gui.class.getResource("/icons/icon.png").toString());
        Image icon = new Image(Gui.class.getResource("/icons/icon.svg").toString());
        stage.getIcons().add(icon);
        stage.show();
    }
}