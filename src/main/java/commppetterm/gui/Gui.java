package commppetterm.gui;

import commppetterm.gui.page.Calendar;
import commppetterm.util.GuiTools;
import javafx.application.Application;
import javafx.stage.Stage;

public final class Gui extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        // GuiTools.prepare(stage, new Example());
        GuiTools.prepare(stage, new Calendar());

        // setze die min Größe der Stage
        stage.setMinWidth(435); // min breite vom Fenster
        stage.setMinHeight(500); // min höhe vom Fenster

        // Optional: anfangsgröße
        stage.setWidth(435); // Anfangsbreite
        stage.setHeight(500); // Anfangshöhe

        stage.show();
    }

    public static void main(String[] args) {
        // Startet die JavaFX-Anwendung
        launch(args);
    }
}