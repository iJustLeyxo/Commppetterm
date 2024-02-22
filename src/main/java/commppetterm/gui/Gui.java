package commppetterm.gui;

import commppetterm.gui.page.CalendarPage;
import commppetterm.gui.page.MonthView;
import org.jetbrains.annotations.NotNull;
import commppetterm.App;
import commppetterm.gui.page.Controller;
import commppetterm.gui.page.DayView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.net.URL;

/**
 * Main class
 */
public final class Gui extends Application {
    /**
     * JavaFX custom start method
     * @param stage Application stage
     * @throws Exception In case of an error
     */
    @Override
    public void start(Stage stage) throws Exception {
        prepare(stage, new CalendarPage());
        stage.show();
    }

    /**
     * Main method
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Prepares a stage
     * @param stage The stage to prepare
     * @param controller The class to load the scene from
     */
    public static void prepare(@NotNull Stage stage, @NotNull Controller controller) throws Exception {
        stage.setScene(new Scene(controller.load()));
        stage.setTitle(App.name);
        String iconFile = "icon.png";
        URL iconUrl = Gui.class.getResource(iconFile);
        if (iconUrl == null) {
            App.logger.warning("Failed to load icon " + iconFile + " from " + Gui.class.getCanonicalName());
        } else {
            Image icon = new Image(iconUrl.toString());
            stage.getIcons().add(icon);
        }
        stage.show();
    }
}