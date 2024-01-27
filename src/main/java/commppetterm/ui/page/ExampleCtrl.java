package commppetterm.ui.page;

import commppetterm.ui.Loadable;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.jetbrains.annotations.NotNull;

/**
 * Example scene controller
 */
public final class ExampleCtrl implements Loadable {
    @FXML
    private Label welcomeText;
    
    @FXML
    private void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
    
    @Override
    public @NotNull String path() {
        return "example.fxml";
    }
}