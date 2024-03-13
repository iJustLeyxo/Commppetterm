package commppetterm.database;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import commppetterm.database.Entry.Recurrence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Database handler
 */
public final class Database {
    /**
     * Database link
     */
    private @NotNull String link = "jdbc:mysql://sql11.freemysqlhosting.net/sql11688847";

    /**
     * Database user
     */
    private @NotNull String user = "sql11688847";

    /**
     * Database password
     */
    private @NotNull String password = "RhiGnaQxx1";

    /**
     * Database connection
     */
    private @Nullable Connection connection;

    /**
     * Database statement
     */
    private @Nullable Statement statement;

    /**
     * Database connection status
     */
    private boolean isConnected;

    /**
     * Initialize a new database
     */
    public Database() {
        generate();
    }

    /**
     * Generates the database and table
     */
    public void generate() {
        // TODO: Generate database
    }

    /**
     * Sets the Connector
     * 
     * @param con Connector
     * @return Succsess of the Conection
     */
    public void getConnection() {
        try {
            this.connection = DriverManager.getConnection(link, user, password);
            this.statement = this.connection.createStatement();
            this.isConnected = true;
        } catch (Exception e) {
            this.isConnected = false;
        }
    }

    /**
     * @return the database urö
     */
    public @NotNull String link() { return this.link; }

    /**
     * @return the database login user
     */
    public @NotNull String user() { return this.user; }

    /**
     * @return the database login password
     */
    public @NotNull String password() { return this.user; }

    /**
     * Updates the database settings
     * 
     * @param link     The new database link
     * @param user     The new database user
     * @param password The new database password
     */
    public void settings(@NotNull String link, @NotNull String user, @NotNull String password) {
        this.link = link;
        this.user = user;
        this.password = password;
    }

    /**
     * Fetches the entries of a day
     * 
     * @param date The day to fetch the entries of
     * @return a list of entries
     */
    public List<Entry> dayEntries(LocalDate date) {
        // TODO: Fetch relevant entries of date, also recurring ones

        LinkedList<Entry> entries = new LinkedList<>();
        entries.add(new Entry("Test Title A", "Test Info A", LocalDateTime.of(2024, 3, 13, 0, 0),
                LocalDateTime.of(2024, 3, 13, 23, 59), null, 0L));
        entries.add(new Entry("Test Title B", "Test Info B", LocalDateTime.of(2024, 3, 13, 5, 0),
                LocalDateTime.of(2024, 3, 13, 10, 0), null, 0L));
        entries.add(new Entry("Test Title C", "Test Info C", LocalDateTime.of(2024, 3, 14, 7, 0),
                LocalDateTime.of(2024, 3, 14, 20, 0), null, 0L));
        return entries;
    }

    /**
     * Fetches the entries of a week
     * 
     * @param date The first day of the week to fetch the entries of
     * @return a list of entries
     */
    public List<Entry> weekEntries(LocalDate date) {
        // TODO: Fetch relevant entries of date, also recurring ones
        return new ArrayList<>();
    }

    /**
     * Creates or edits and entry
     * 
     * @param entry The entry to create or edit
     */
    public void save(@Nullable Entry entry) {
        Integer Wiederholung = 0;
        if (entry.recurring()!=null){
            if (entry.recurring().type() == Recurrence.Type.DAY) {
                Wiederholung = (int) entry.recurring().frequency();
            } else if (entry.recurring().type() == Recurrence.Type.WEEK) {
                Wiederholung = (int) entry.recurring().frequency() * 7;
            } else if (entry.recurring().type() == Recurrence.Type.MONTH) {
                Wiederholung = (int) entry.recurring().frequency() * 31;
            } else if (entry.recurring().type() == Recurrence.Type.YEAR) {
                Wiederholung = (int) entry.recurring().frequency() * 365;
            }
        }
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        String Time_Start = dtf.format(entry.start());
        String Time_Ende = dtf.format(entry.end());
        getConnection();
        if (this.isConnected == true) {
            String SQLStament = "INSERT INTO `Termine`(`Name`, `Wiederholung`, `Farbe`, `DatumStart`, `DatumEnde`, `Notiz`, `Ort`, `Benutzer`) VALUES ('" + entry.title() + "', '"
                    + Wiederholung + "', '0', '" + Time_Start + "', '" + Time_Ende + "', '" + entry.info()
                    + "', 'Null', '0')";
            try {
                this.statement.execute(SQLStament);
            } catch (Exception e) {
                System.out.println("Konnte eintarg nicht Speichern: " + e);
            }
        } else {

        }
    }

    /**
     * Deletes an entry
     * 
     * @param entry The entry to delete
     */
    public void delete(@Nullable Entry entry) {
        // TODO: Delete entry
    }
}
