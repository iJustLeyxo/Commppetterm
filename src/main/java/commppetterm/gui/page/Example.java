package commppetterm.gui.page;

// import org.jetbrains.annotations.NotNull;

import javafx.fxml.FXML;
import javafx.scene.control.Label;


public final class Example implements Controller {
    @Override
    public String path() {
        return "/commppetterm/gui/page/example.fxml";
    }
    
    @FXML
    public void initialize() {
        // Initialisierungslogik hier
    }

    @Override
    public void preInit() {
        // Optional: Code vor dem Laden
    }
    
    @Override
    public void postInit() {
        // Optional: Code nach dem Laden
    }

    @FXML
    private Label welcomeText;

    @FXML
    private void onHelloButtonClick() {
        welcomeText.setText("Hello world!");
    }

    
}