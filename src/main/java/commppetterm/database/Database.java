package commppetterm.database;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.Connector;
import java.sql.Connection;
import java.sql.DriverManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Database handler
 */
public final class Database {
    private @NotNull String link = "jdbc:mysql://sql11.freemysqlhosting.net/sql11688847";

    private @NotNull String user = "sql11688847";

    private @NotNull String password = "RhiGnaQxx1";

    private Connector con;

    private boolean isConnected = false;

    /**
     * Sets the Connector con and the isConnected
     */
    public void getConnection() {
        try {
            this.con = DriverManager.getConnection(link, user, password);
            this.isConnected = true;
        } catch (Exception e) {
            this.isConnected = false;
        }
    }

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
        entries.add(new Entry("Test Title A", "Test Info A", LocalDateTime.of(2024, 3, 13, 0, 0), LocalDateTime.of(2024, 3, 13, 23, 59), null, 0L));
        entries.add(new Entry("Test Title B", "Test Info B", LocalDateTime.of(2024, 3, 13, 5, 0), LocalDateTime.of(2024, 3, 13, 10, 0), null, 0L));
        entries.add(new Entry("Test Title C", "Test Info C", LocalDateTime.of(2024, 3, 14, 7, 0), LocalDateTime.of(2024, 3, 14, 20, 0), null, 0L));
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
     * @param entry The entry to create or edit
     */
    public void save(@Nullable Entry entry) {
        // TODO: Update or create entry if it doesn't yet exist
        Integer Wiederholung;
        if(entry.recurring().getType()==Recurrence.Type.DAY){
            Wiederholung = (int) entry.recurring().getFrequency();
        }else if (entry.recurring().getType()==Recurrence.Type.WEEK){
            Wiederholung = (int) entry.recurring().getFrequency() * 7;
        }else if (entry.recurring().getType()==Recurrence.Type.MONTH){
            Wiederholung = (int) entry.recurring().getFrequency() * 31;
        }else if (entry.recurring().getType()==Recurrence.Type.YEAR){
            Wiederholung = (int) entry.recurring().getFrequency() * 365;
        }
         String Time_Start = entry.start();
        if(this.isConnected==true){
            String SQLStament = "INSERT INTO `Termine` VALUES ('"+ entry.id() +"', "+ entry.title()+"', '"+ Wiederholung +"', 1', " + 
        }else{

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
