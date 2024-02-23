package commppetterm.gui;

import java.io.IOException;

import org.jetbrains.annotations.NotNull;

import commppetterm.Commppetterm;
import commppetterm.gui.page.Controller;
import commppetterm.gui.page.DayView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public final class Gui extends Application {
    @Override
    public void start(Stage stage) throws Exception {
    
        // // setze die min Größe der Stage
        stage.setMinWidth(400); // min breite vom Fenster
        stage.setMinHeight(790); // min höhe vom Fenster

        // // Optional: anfangsgröße
        stage.setWidth(420); // Anfangsbreite
        stage.setHeight(800); // Anfangshöhe

        // prepare(stage, new DayView());
        prepare(stage, new DayView());

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