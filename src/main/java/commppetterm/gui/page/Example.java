package commppetterm.gui.page;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.jetbrains.annotations.NotNull;

/**
 * Example scene controller
 */
public final class Example implements Controller {
    @FXML
    private Label welcomeText;
    
    @FXML
    private void onHelloButtonClick() {
        welcomeText.setText("Hello world!");
    }
    
    @Override
    public @NotNull String path() {
        return "example.fxml";
    }
}