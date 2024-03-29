package commppetterm.gui.page;

import commppetterm.database.Entry;
import commppetterm.database.exception.DatabaseException;
import commppetterm.gui.exception.EditorException;
import javafx.scene.control.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.material2.Material2AL;
import org.kordamp.ikonli.material2.Material2MZ;

import commppetterm.gui.App;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.HBox;

import java.sql.SQLException;
import java.time.*;
import java.util.Optional;


/**
 * Controller for entry editor
 */
public final class Editor extends Controller {
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
     */
    public Editor() {
        if (App.get().entry() != null && App.get().entry().id() != null) {
            Entry entry = App.get().entry();

            boolean end = entry.end() != null;
            boolean recurring = entry.recurring() != null;
            boolean time = !((entry.start().getHour() == 0 && entry.start().getMinute() == 0) &&
                    (entry.end() == null || (entry.end().getHour() == 23 && entry.end().getMinute() == 59)));

            this.title.setText(entry.title());
            this.info.setText(entry.info());

            this.startDay.setText(Integer.toString(entry.start().getDayOfMonth()));
            this.startMonth.setText(Integer.toString(entry.start().getMonthValue()));
            this.startYear.setText(Integer.toString(entry.start().getYear()));
            this.startHour.setText(Integer.toString(entry.start().getHour()));
            this.startMinute.setText(Integer.toString(entry.start().getMinute()));

            this.endDay.setText(Integer.toString(entry.end().getDayOfMonth()));
            this.endMonth.setText(Integer.toString(entry.end().getMonthValue()));
            this.endYear.setText(Integer.toString(entry.start().getYear()));
            this.endHour.setText(Integer.toString(entry.end().getHour()));
            this.endMinute.setText(Integer.toString(entry.end().getMinute()));

            this.yearly.setSelected(false);
            this.monthly.setSelected(false);
            this.weekly.setSelected(false);
            this.daily.setSelected(false);

            if (recurring) {
                switch (entry.recurring().type()) {
                    case DAY -> this.daily.setSelected(true);
                    case WEEK -> this.weekly.setSelected(true);
                    case MONTH -> this.monthly.setSelected(true);
                    case YEAR -> this.yearly.setSelected(true);
                }

                this.frequency.setText(Byte.toString(entry.recurring().frequency()));
            } else {
                this.yearly.setSelected(true);
                this.frequency.setText("1");
            }

            this.enabled(this.startTime, time);
            this.enabled(this.endTime, time);
            this.enabled(this.endBox, end);
            this.enabled(this.recurringBox, recurring);

            this.delete.setDisable(false);

            this.end.setSelected(end);
            this.time.setSelected(time);
            this.recurring.setSelected(recurring);
        } else {
            this.delete.setDisable(true);
            this.yearly.setSelected(true);
        }

        this.end();
        this.time();
        this.recurring();
    }

    @FXML
    private void save() {
        try {
            App.get().database().save(this.entry());
            App.get().provider(new Calendar());
        } catch (DatabaseException e) {
            Optional<ButtonType> res = App.get().alert(e, Alert.AlertType.ERROR, "Go to settings?", ButtonType.YES, ButtonType.NO);

            if (res.isPresent() && res.get().equals(ButtonType.YES)) {
                App.get().provider(new Settings());
            }
        } catch (EditorException e) {
            Optional<ButtonType> res = App.get().alert(e, Alert.AlertType.ERROR, "Close without saving?", ButtonType.YES, ButtonType.NO);

            if (res.isPresent() && res.get().equals(ButtonType.YES)) {
                App.get().provider(new Calendar());
            }
        }
    }

    @FXML
    private void delete() {
        try {
            App.get().database().delete(this.entry());
            App.get().provider(new Calendar());
        } catch (DatabaseException e) {
            Optional<ButtonType> res = App.get().alert(e, Alert.AlertType.ERROR, "Go to settings?", ButtonType.YES, ButtonType.NO);

            if (res.isPresent() && res.get().equals(ButtonType.YES)) {
                App.get().provider(new Settings());
            }
        } catch (EditorException e) {
            Optional<ButtonType> res = App.get().alert(e, Alert.AlertType.ERROR, "Close without saving?", ButtonType.YES, ButtonType.NO);

            if (res.isPresent() && res.get().equals(ButtonType.YES)) {
                App.get().provider(new Calendar());
            }
        }
    }

    @FXML
    private void cancel() {
        App.get().provider(new Calendar());
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
    private @NotNull Entry entry() throws EditorException {
        /* Recurring */
        Entry.Recurring recurring = entryRecurrence();

        /* Date */
        LocalDate startDate, endDate;

        try {
            /* Start Date */
            startDate = LocalDate.of(
                    Integer.parseInt(this.startYear.getText()),
                    Integer.parseInt(this.startMonth.getText()),
                    Integer.parseInt(this.startDay.getText())
            );
        } catch (NumberFormatException | DateTimeException e) {
            throw new EditorException("Start date is invalid.");
        }

        try {
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
        } catch (NumberFormatException | DateTimeException e) {
            throw new EditorException("End date is invalid.");
        }

        if (recurring != null) {
            if (
                    recurring.type() == Entry.Recurring.RecurringType.DAY && Period.between(startDate, endDate).getDays() >= recurring.frequency() ||
                            recurring.type() == Entry.Recurring.RecurringType.WEEK && Period.between(startDate, endDate).getDays() / 7 >= recurring.frequency() ||
                            recurring.type() == Entry.Recurring.RecurringType.MONTH && Period.between(startDate, endDate).getMonths() >= recurring.frequency() ||
                            recurring.type() == Entry.Recurring.RecurringType.YEAR && Period.between(startDate, endDate).getYears() >= recurring.frequency()
            ) {
                throw new EditorException("Entry cannot be longer than recurrence frequency.");
            }
        }

        /* Time */
        LocalTime startTime, endTime;

        try {
            /* Start Time */
            if (this.time.isSelected()) {
                startTime = LocalTime.of(
                        Integer.parseInt(this.startHour.getText()),
                        Integer.parseInt(this.startMinute.getText())
                );
            } else {
                startTime = LocalTime.of(0, 0);
            }
        } catch (NumberFormatException | DateTimeException e) {
            throw new EditorException("Start time is invalid.");
        }

        try {
            /* End Time */
            if (this.end.isSelected() && this.time.isSelected()) {
                endTime = LocalTime.of(
                        Integer.parseInt(this.endHour.getText()),
                        Integer.parseInt(this.endMinute.getText())
                );
            } else {
                endTime = LocalTime.of(23, 59);
            }
        } catch (NumberFormatException | DateTimeException e) {
            throw new EditorException("End time is invalid.");
        }

        LocalDateTime startPoint = LocalDateTime.of(startDate, startTime);
        LocalDateTime endPoint = LocalDateTime.of(endDate, endTime);

        if (endPoint.isBefore(startPoint)) {
            throw new EditorException("End cannot be before start.");
        }

        /* ID */
        Long id = null;

        if (App.get().entry() != null) {
            id = App.get().entry().id();
        }

        App.get().provider(new Calendar());

        /* Generate entry */
        return new Entry(
                id,
                this.title.getText(),
                this.info.getText(),
                startPoint,
                endPoint,
                recurring
        );
    }

    /**
     * Generates the recurring object of an entry
     * @return the recurring object
     */
    private @Nullable Entry.Recurring entryRecurrence() throws EditorException {
        Entry.Recurring recurring = null;

        if (this.recurring.isSelected()) {
            Entry.Recurring.RecurringType recurringType;
            if (this.yearly.isSelected()) {
                recurringType = Entry.Recurring.RecurringType.YEAR;
            } else if (this.monthly.isSelected()) {
                recurringType = Entry.Recurring.RecurringType.MONTH;
            } else if (this.weekly.isSelected()) {
                recurringType = Entry.Recurring.RecurringType.WEEK;
            } else {
                recurringType = Entry.Recurring.RecurringType.DAY;
            }

            byte freq;

            try {
                freq = Byte.parseByte(this.frequency.getText());
            } catch (NumberFormatException e) {
                throw new EditorException("Frequency must be a number.");
            }

            if (freq < 1) {
                throw new EditorException("Frequency must be positive.");
            }

            recurring = new Entry.Recurring(recurringType, freq);
        }
        return recurring;
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
}
