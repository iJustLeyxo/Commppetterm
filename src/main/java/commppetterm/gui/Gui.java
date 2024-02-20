package commppetterm.gui;

import commppetterm.Commppetterm;
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
    public static void prepare(@NotNull Stage stage, @NotNull Controller controller) {
        stage.setScene(new Scene(new MonthView().load()));
        stage.setTitle(Commppetterm.name);
        URL iconUrl = Gui.class.getResource("icon.png");
        if (iconUrl == null) {
            // TODO: Log warning
        }
        Image icon = new Image(iconUrl.toString());
        stage.getIcons().add(icon);
        stage.show();
    }
}