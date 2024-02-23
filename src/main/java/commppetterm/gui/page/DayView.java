package commppetterm.gui.page;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.jetbrains.annotations.NotNull;

import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public final class DayView implements Controller {
    @Override
    public @NotNull String path() {
        return "/commppetterm/gui/page/DayView.fxml";
    }


    @FXML 
    private Label lblDate;

    @FXML
    private GridPane dayGrid;

    public void initialize() {
        // Setze das aktuelle Datum im gewünschten Format
        LocalDate heute = LocalDate.now();
        // Erstelle einen DateTimeFormatter mit dem gewünschten Ausgabeformat
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, dd. MMMM", Locale.GERMANY);
        String formattedDate = heute.format(formatter);
        lblDate.setText(formattedDate);
    }

    @FXML
    private void handleOpenDialog() {
        Dialog<Ereignis> dialog = new Dialog<>();
        dialog.setTitle("Ereignis Details");
        dialog.setHeaderText("Bitte geben Sie die Details des Ereignisses ein:");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField txtTitel = new TextField();
        TextField txtAnfang = new TextField();
        TextField txtEnde = new TextField();

        grid.add(new Label("Titel:"), 0, 0);
        grid.add(txtTitel, 1, 0);
        grid.add(new Label("Anfang:"), 0, 1);
        grid.add(txtAnfang, 1, 1);
        grid.add(new Label("Ende:"), 0, 2);
        grid.add(txtEnde, 1, 2);

        dialog.getDialogPane().setContent(grid);

        // Konvertiere das Ergebnis in ein Ereignis, wenn der OK-Button geklickt wurde
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                return new Ereignis(txtTitel.getText(), txtAnfang.getText(), txtEnde.getText());
            }
            return null;
        });

        dialog.showAndWait().ifPresent(ereignis -> {
            Label newEventLabel = new Label(ereignis.getTitel() + ": " + ereignis.getAnfang() + " - " + ereignis.getEnde());
            int nextRow = dayGrid.getRowCount() + 1; // Bestimme die nächste verfügbare Zeile
            dayGrid.add(newEventLabel, 1, nextRow); // Füge das Ereignis hinzu
        });        

    }





    public static class Ereignis {
        private String titel, anfang, ende;

        public Ereignis(String titel, String anfang, String ende) {
            this.titel = titel;
            this.anfang = anfang;
            this.ende = ende;
        }

        public String getTitel() { return titel; }
        public String getAnfang() { return anfang; }
        public String getEnde() { return ende; }
    }
}
