package commppetterm.database;

import commppetterm.gui.App;
import commppetterm.gui.page.Settings;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import commppetterm.database.Entry.Recurrence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
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
     * Database user details
     */
    private @NotNull String user = "sql11688847", password = "RhiGnaQxx1";

    /**
     * Database and table names
     */
    private @NotNull String database = "sql11688847", table = "calendar";

    /**
     * Database connection
     */
    private @Nullable Connection connection;

    /**
     * Database statement
     */
    private @Nullable Statement statement;

    /**
     * Initialize a new database
     */
    public Database() {
        this.connection = null;
        connect();
    }

    /**
     * Connects to the database
     */
    public void connect() {
        if (this.connected()) {
            try {
                this.disconnect();
            } catch (SQLException ignored) {}
        }

        try {
            this.connection = DriverManager.getConnection(link, user, password);
            this.statement = this.connection.createStatement();
        } catch (SQLException ignored) {}

        // TODO: Generate database
    }

    /**
     * Disconnects from the database
     */
    public void disconnect() throws SQLException {
        if (this.statement != null) {
            this.statement.close();
            this.statement = null;
        }
        
        if (this.connection != null) {
            this.connection.close();
            this.connection = null;
        }
    }

    /**
     * @return {@code true} if the database has been connected
     */
    public boolean connected() { return this.connection != null && this.statement != null; }

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
     * @return the database name
     */
    public @NotNull String database() { return this.database; }

    /**
     * @return the table name
     */
    public @NotNull String table() { return this.table; }

    /**
     * Updates the database settings
     * @param link The new database link
     * @param user The new database user
     * @param password The new database password
     */
    public void settings(@NotNull String link, @NotNull String user, @NotNull String password, @NotNull String database, @NotNull String table) {
        this.link = link;
        this.user = user;
        this.password = password;
        this.database = database;
        this.table = table;
    }

    /**
     * Fetches the entries of a day
     * @param date The day to fetch the entries of
     * @return a list of entries
     */
    public List<Entry> dayEntries(LocalDate date) {
        LinkedList<Entry> entries = new LinkedList<>();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String Time = dtf.format(date); 
        String Timenegativ = dtf.format(date.minusDays(1));
        if(this.connected()){
            String SQLStatement = "SELECT * FROM `Termine` WHERE DatumStart<='" + Time + "' AND DatumEnde>='" + Timenegativ + "'";
            try {
                ResultSet result = statement.executeQuery(SQLStatement);
                DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
                while (result.next()) {
                    
                    LocalDateTime Time_Start = LocalDateTime.parse(result.getString("DatumStart"), dtf2);
                    LocalDateTime Time_Ende = LocalDateTime.parse(result.getString("DatumEnde"), dtf2);
                    entries.add(new Entry(result.getString("Name"), result.getString("Notiz"), Time_Start, Time_Ende, null, result.getLong("TerimNr")));
                }  
            } catch (Exception e) {
                System.out.println("Konnte keine ergebnisse laden:" + e);
            }
        }else{

        }
        return entries;
    }

    /**
     * Creates or edits and entry
     * @param entry The entry to create or edit
     */
    public void save(@Nullable Entry entry) {
        if (entry == null || !this.connected()) {
            App.get().controller(new Settings());
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        String title = entry.title();
        String info = entry.info();
        String start = formatter.format(entry.start());
        String end = formatter.format(entry.end());
        String recurringType = "NULL";
        String recurringFrequency = "1";

        if (entry.recurring() != null) {
            recurringType = entry.recurring().type().toString();
            recurringFrequency = Integer.toString(entry.recurring().frequency());
        }

        try {
            String SQLStatement = "INSERT INTO Termine (" +
                    "Name, Notiz, DatumStart, DatumEnde, Wiederholung, Farbe, Ort, Benutzer" +
                    ") VALUES ('" +
                    title + "', '" +
                    info + "', '" +
                    start + "', '" +
                    end + "', '" +
                    recurringFrequency + "', '0', 'Null', '0')";
            this.statement.execute(SQLStatement);
        } catch (SQLException e) {
            App.get().LOGGER.warning(e.getMessage());
        }
    }

    /**
     * Deletes an entry
     * @param entry The entry to delete
     */
    public void delete(@Nullable Entry entry) {
        // TODO: Delete entry
    }
}
