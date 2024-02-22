package commppetterm.gui;

import commppetterm.App;
import commppetterm.gui.page.Controller;
import commppetterm.gui.page.MonthView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.net.URL;

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
     * Prepares a stage
     * @param stage The stage to prepare
     * @param controller The class to load the scene from
     */
    public static void prepare(@NotNull Stage stage, @NotNull Controller controller) throws Exception {
        stage.setScene(new Scene(new MonthView().load()));
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