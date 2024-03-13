package commppetterm.gui.page;

import commppetterm.database.Entry;
import commppetterm.gui.exception.EditorException;
import org.jetbrains.annotations.NotNull;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.material2.Material2AL;
import org.kordamp.ikonli.material2.Material2MZ;

import commppetterm.gui.App;
import commppetterm.gui.exception.FxmlLoadException;
import commppetterm.gui.exception.URLNotFoundException;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


/**
 * Controller for entry editor
 */
public final class Editor extends Controller {
    /**
     * Edit mode or create mode
     */
    private final @NotNull Mode mode;

    @FXML
    private HBox startTime, endBox, endTime, recurringBox;

    @FXML
    private Button delete;

    @FXML
    private ToggleButton end, time, recurring, yearly, monthly, weekly, daily;

    @FXML
    private FontIcon startIcon, endIcon, recurringIcon;

    @FXML
    private TextField title, info,
            startDay, startMonth, startYear, startHour, startMinute,
            endDay, endMonth, endYear, endHour, endMinute,
            frequency;

    /**
     * Creates a new editor
     * @param mode Edit or create mode?
     */
    public Editor(@NotNull Mode mode) throws URLNotFoundException, FxmlLoadException {
        this.mode = mode;

        this.end();
        this.time();
        this.recurring();
        this.yearly.setSelected(true);
        this.frequency.setText("1");

        if (this.mode == Mode.EDIT && App.get().entry() != null) {
            Entry entry = App.get().entry();

            boolean end = entry.end() != null;
            boolean recurring = entry.recurring() != null;
            boolean time = (!(entry.start().getHour() == 0 && entry.start().getMinute() == 0) &&
                    (entry.end() == null || (entry.end().getHour() == 23 && entry.end().getMinute() == 59)));

            this.title.setText(entry.title());
            this.info.setText(entry.info());

            this.startDay.setText(Integer.toString(entry.start().getDayOfMonth()));
            this.startMonth.setText(Integer.toString(entry.start().getMonthValue()));
            this.startYear.setText(Integer.toString(entry.start().getYear()));
            this.startHour.setText(Integer.toString(entry.start().getHour()));
            this.startMinute.setText(Integer.toString(entry.start().getMinute()));

            if (end) {
                this.endDay.setText(Integer.toString(entry.start().getDayOfMonth()));
                this.endMonth.setText(Integer.toString(entry.start().getMonthValue()));
                this.endYear.setText(Integer.toString(entry.start().getYear()));
                this.endHour.setText(Integer.toString(entry.end().getHour()));
                this.endMinute.setText(Integer.toString(entry.end().getMinute()));
            }

            if (recurring) {
                switch (entry.recurring().type()) {
                    case DAY -> this.daily.setSelected(true);
                    case WEEK -> this.weekly.setSelected(true);
                    case MONTH -> this.monthly.setSelected(true);
                    case YEAR -> this.yearly.setSelected(true);
                }

                this.frequency.setText(Byte.toString(entry.recurring().frequency()));
            }

            this.enabled(this.startTime, time);
            this.enabled(this.endTime, time);
            this.enabled(this.endBox, end);
            this.enabled(this.recurringBox, recurring);

            this.delete.setDisable(true);

            this.end.setSelected(end);
            this.time.setSelected(time);
            this.recurring.setSelected(recurring);
        }
    }

    @FXML
    private void save() throws URLNotFoundException, FxmlLoadException {
        try {
            App.get().database().save(this.entry());
            App.get().controller(new Calendar());
        } catch (EditorException ignored) {}
    }

    @FXML
    private void delete() throws URLNotFoundException, FxmlLoadException {
        try {
            App.get().database().delete(this.entry());
            App.get().controller(new Calendar());
        } catch (EditorException ignored) {}
    }

    @FXML
    private void cancel() throws URLNotFoundException, FxmlLoadException {
        App.get().controller(new Calendar());
    }

    @FXML
    private void end() {
        if (this.end.isSelected()) {
            this.startIcon.setIconCode(Material2AL.FIRST_PAGE);
        } else {
            this.startIcon.setIconCode(Material2MZ.TODAY);

            if (this.time.isSelected()) {
                this.time.setSelected(false);
                this.time();
            }
        }

        this.enabled(this.endIcon, this.end.isSelected());
        this.enabled(this.endBox, this.end.isSelected());
        App.get().stage().sizeToScene();
    }

    @FXML
    private void time() {
        if (this.time.isSelected() && !this.end.isSelected()) {
            this.end.setSelected(true);
            this.end();
        }

        this.enabled(this.startTime, this.time.isSelected());
        this.enabled(this.endTime, this.time.isSelected());
        App.get().stage().sizeToScene();
    }

    @FXML
    private void recurring() {
        this.enabled(recurringIcon, this.recurring.isSelected());
        this.enabled(recurringBox, this.recurring.isSelected());
        App.get().stage().sizeToScene();
    }

    @FXML
    private void yearly() {
        if (this.yearly.isSelected()) {
            this.daily.setSelected(false);
            this.weekly.setSelected(false);
            this.monthly.setSelected(false);
        } else {
            this.yearly.setSelected(true);
        }
    }

    @FXML
    private void monthly() {
        if (this.monthly.isSelected()) {
            this.daily.setSelected(false);
            this.weekly.setSelected(false);
            this.yearly.setSelected(false);
        } else {
            this.monthly.setSelected(true);
        }
    }

    @FXML
    private void weekly() {
        if (this.weekly.isSelected()) {
            this.daily.setSelected(false);
            this.monthly.setSelected(false);
            this.yearly.setSelected(false);
        } else {
            this.weekly.setSelected(true);
        }
    }

    @FXML
    private void daily() {
        if (this.daily.isSelected()) {
            this.weekly.setSelected(false);
            this.monthly.setSelected(false);
            this.yearly.setSelected(false);
        } else {
            this.daily.setSelected(true);
        }
    }

    /**
     * Generates an entry from the editor contents
     * @return an entry object
     */
    private @NotNull Entry entry() throws URLNotFoundException, FxmlLoadException, DateTimeException {
        /* Recurring */
        Entry.Recurring recurring = null;

        if (this.recurring.isSelected()) {
            Entry.Recurring.Type type;
            if (this.yearly.isSelected()) {
                type = Entry.Recurring.Type.YEAR;
            } else if (this.monthly.isSelected()) {
                type = Entry.Recurring.Type.MONTH;
            } else if (this.weekly.isSelected()) {
                type = Entry.Recurring.Type.WEEK;
            } else {
                type = Entry.Recurring.Type.DAY;
            }
            byte freq = Byte.parseByte(this.frequency.getText());

            if (freq < 1) {
                throw new EditorException("Negative recurrence frequency is not allowed.");
            }

            recurring = new Entry.Recurring(type, freq);
        }

        LocalDate startDate, endDate;
        LocalTime startTime, endTime;

        try {
            /* Start Date */
            startDate = LocalDate.of(
                    Integer.parseInt(this.startYear.getText()),
                    Integer.parseInt(this.startMonth.getText()),
                    Integer.parseInt(this.startDay.getText())
            );

            /* Start Time */
            if (this.time.isSelected()) {
                startTime = LocalTime.of(
                        Integer.parseInt(this.startHour.getText()),
                        Integer.parseInt(this.startMinute.getText())
                );
            } else {
                startTime = LocalTime.of(0, 0);
            }

            /* End Date */
            if (this.end.isSelected()) {
                endDate = LocalDate.of(
                        Integer.parseInt(this.endYear.getText()),
                        Integer.parseInt(this.endMonth.getText()),
                        Integer.parseInt(this.endDay.getText())
                );
            } else {
                endDate = startDate;
            }

            /* End Time */
            if (this.end.isSelected() && this.time.isSelected()) {
                endTime = LocalTime.of(
                        Integer.parseInt(this.endHour.getText()),
                        Integer.parseInt(this.endMinute.getText())
                );
            } else {
                endTime = LocalTime.of(23, 59);
            }
        } catch (DateTimeException | NumberFormatException e) {
            throw new EditorException(e);
        }

        /* ID */
        Long id;
        if (this.mode == Mode.EDIT) {
            assert App.get().entry() != null;
            id = App.get().entry().id();
        } else {
            id = null;
        }

        App.get().controller(new Calendar());

        /* Generate entry */
        return new Entry(
                id,
                this.title.getText(),
                this.info.getText(),
                LocalDateTime.of(startDate, startTime),
                LocalDateTime.of(endDate, endTime),
                recurring
        );
    }

    /**
     * Enables or disables a node, can make the node invisible and exclude the node from rendering
     * @param node The node to edit
     * @param value The value to set
     */
    private void enabled(Node node, boolean value) {
        node.setVisible(value);
        node.setManaged(value);
    }

    /**
     * Editor mode
     */
    public enum Mode {CREATE, EDIT}
}
