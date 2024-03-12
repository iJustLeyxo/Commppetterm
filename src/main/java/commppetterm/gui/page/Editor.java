package commppetterm.gui.page;

import commppetterm.App;
import commppetterm.database.Database;
import commppetterm.database.Entry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.material2.Material2AL;
import org.kordamp.ikonli.material2.Material2MZ;

import commppetterm.gui.Gui;
import commppetterm.gui.exception.ControllerLoadedException;
import commppetterm.gui.exception.FxmlLoadException;
import commppetterm.gui.exception.URLNotFoundException;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;

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
    private Button save, delete;

    @FXML
    private ToggleButton end, time, recurring, yearly, monthly, weekly, daily;

    @FXML
    private FontIcon startIcon, endIcon, recurringIcon;

    @FXML
    private TextField title, info,
            startDay, startMonth, startYear, startHour, startMinute,
            endDay, endMonth, endYear, endHour, endMinute, frequency;

    /**
     * Creates a new editor
     * @param mode Edit or create mode?
     */
    public Editor(@NotNull Mode mode) {
        this.mode = mode;
    }

    @FXML
    private void save() throws ControllerLoadedException, URLNotFoundException, FxmlLoadException {
        Database.save(this.generate());
    }

    @FXML
    private void delete() throws ControllerLoadedException, URLNotFoundException, FxmlLoadException {
        Database.delete(this.generate());
    }

    @FXML
    private void cancel() throws ControllerLoadedException, URLNotFoundException, FxmlLoadException {
        Gui.get().swap(new Calendar());
    };

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
        Gui.get().stage().sizeToScene();
    }

    @FXML
    private void time() {
        if (this.time.isSelected() && !this.end.isSelected()) {
            this.end.setSelected(true);
            this.end();
        }

        this.enabled(this.startTime, this.time.isSelected());
        this.enabled(this.endTime, this.time.isSelected());
        Gui.get().stage().sizeToScene();
    }

    @FXML
    private void recurring() {
        this.enabled(recurringIcon, this.recurring.isSelected());
        this.enabled(recurringBox, this.recurring.isSelected());
        Gui.get().stage().sizeToScene();
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

    @Override
    protected void init() {
        this.end();
        this.time();
        this.recurring();
        this.yearly.setSelected(true);
        this.frequency.setText("1");

        if (this.mode == Mode.EDIT) {
            this.delete.setDisable(true);
        } else {
            // TODO: Load data from entry if in edit mode
        }
    }

    /**
     * Generates an entry from the editor contents
     * @return an entry object
     */
    private @Nullable Entry generate() throws ControllerLoadedException, URLNotFoundException, FxmlLoadException {
        try {
            /* Recurring */
            Entry.Repeat repeat = null;

            if (this.recurring.isSelected()) {
                Entry.Repeat.Type type;
                if (this.yearly.isSelected()) {
                    type = Entry.Repeat.Type.YEAR;
                } else if (this.monthly.isSelected()) {
                    type = Entry.Repeat.Type.MONTH;
                } else if (this.weekly.isSelected()) {
                    type = Entry.Repeat.Type.WEEK;
                } else {
                    type = Entry.Repeat.Type.DAY;
                }
                byte freq = Byte.parseByte(this.frequency.getText());
                repeat = new Entry.Repeat(type, freq);
            }

            /* Start Date */
            LocalDate startDate = LocalDate.of(
                    Integer.parseInt(this.startYear.getText()),
                    Integer.parseInt(this.startMonth.getText()),
                    Integer.parseInt(this.startDay.getText())
            );

            /* Start Time */
            LocalTime startTime;
            if (this.time.isSelected()) {
                startTime = LocalTime.of(
                        Integer.parseInt(this.startHour.getText()),
                        Integer.parseInt(this.startMinute.getText())
                );
            } else {
                startTime = LocalTime.of(0, 0);
            }

            /* End Date */
            LocalDate endDate;
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
            LocalTime endTime;
            if (this.end.isSelected() && this.time.isSelected()) {
                endTime = LocalTime.of(
                        Integer.parseInt(this.endHour.getText()),
                        Integer.parseInt(this.endMinute.getText())
                );
            } else {
                endTime = LocalTime.of(23, 59);
            }

            /* ID */
            Long id;
            if (this.mode == Mode.EDIT) {
                assert Gui.entry != null;
                id = Gui.entry.id();
            } else {
                id = null;
            }

            Gui.get().swap(new MonthPage());

            /* Generate entry */
            return new Entry(
                    this.title.getText(),
                    this.info.getText(),
                    LocalDateTime.of(startDate, startTime),
                    LocalDateTime.of(endDate, endTime),
                    repeat,
                    id
            );
        } catch (NumberFormatException e) {
            return null;
        }
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
    public static enum Mode {CREATE, EDIT}
}
