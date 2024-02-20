package commppetterm.gui;

import commppetterm.Commppetterm;
import commppetterm.gui.page.Controller;
import commppetterm.gui.page.MonthView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public final class Gui extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        prepare(stage, new MonthView());
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

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
        stage.setScene(new Scene(load(controller)));
        stage.setTitle(Commppetterm.name);
        Image icon = new Image(Gui.class.getResource("icon.png").toString());
        stage.getIcons().add(icon);
        stage.show();
    }
}