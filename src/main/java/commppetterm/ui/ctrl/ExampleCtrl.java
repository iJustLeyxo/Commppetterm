package commppetterm.ui.ctrl;

import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Example scene controller
 */
public final class ExampleCtrl {
    @FXML
    private Label welcomeText;
    
    @FXML
    private void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}