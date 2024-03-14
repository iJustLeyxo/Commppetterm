package commppetterm.database;

import commppetterm.gui.App;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

/**
 * Database handler
 */
public final class Database {
    /**
     * Database settings
     */
    private @NotNull String url, database, table, user, password;

    /**
     * Initialize a new database
     */
    public Database(@NotNull String url, @NotNull String database, @NotNull String table, @NotNull String user, @NotNull String password) {
        this.url = url;
        this.database = database;
        this.table = table;
        this.user = user;
        this.password = password;
        this.init();
    }

    /**
     * @return the database url
     */
    public @NotNull String url() { return this.url; }

    /**
     * @return the database name
     */
    public @NotNull String database() { return this.database; }

    /**
     * @return the table name
     */
    public @NotNull String table() { return this.table; }

    /**
     * @return the database login user
     */
    public @NotNull String user() { return this.user; }

    /**
     * @return the database login password
     */
    public @NotNull String password() { return this.password; }

    /**
     * Updates the database settings
     * @param url Server url
     * @param database Database
     * @param table Calendar table
     * @param user Login user
     * @param password Login password
     */
    public boolean update(@NotNull String url, @NotNull String database, @NotNull String table, @NotNull String user, @NotNull String password) {
        this.url = url;
        this.database = database;
        this.table = table;
        this.user = user;
        this.password = password;
        return this.init();
    }

    /**
     * Initializes the required tables
     * @return {@code true} if the initialization succeeded
     */
    private boolean init() {
        String sql = "CREATE TABLE IF NOT EXISTS " + this.table + " (" +
                "id INTEGER AUTO_INCREMENT PRIMARY KEY," +
                "title TEXT NOT NULL," +
                "info TEXT NOT NULL," +
                "start DATETIME NOT NULL," +
                "end DATETIME," +
                "recurringType TEXT CHECK(recurringType IN (null, 'YEAR', 'MONTH', 'WEEK', 'DAY'))," +
                "recurringFrequency INTEGER);";

        return this.execute(sql);
    }

    /**
     * Fetches the entries of a day
     * @param date The day to fetch the entries of
     * @return a list of entries
     */
    public List<Entry> entries(LocalDate date) {
        LinkedList<Entry> entries = new LinkedList<>();

        try {
            DateTimeFormatter queryFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            String endPoint = queryFormatter.format(date) + " 23:59:99";
            String startTime = queryFormatter.format(date) + " 00:00:00";

            String sql = "SELECT * FROM " + this.table + " WHERE start <= '" + endPoint + "' AND end >= '" + startTime + "';";
            ResultSet res = this.query(sql);

            if (res == null) {
                App.get().LOGGER.warning("Reading entries from empty connection.");
                return entries;
            }

            DateTimeFormatter resultFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            while (res.next()) {
                LocalDateTime start = LocalDateTime.parse(res.getString("start"), resultFormatter);
                LocalDateTime end = LocalDateTime.parse(res.getString("end"), resultFormatter);
                Entry.Recurring recurring = null;
                Entry.Recurring.Type recurringType = null;
                byte recurringFrequency = res.getByte("recurringFrequency");

                switch (res.getString("recurringType")) {
                    case "YEAR" -> recurringType = Entry.Recurring.Type.YEAR;
                    case "MONTH" -> recurringType = Entry.Recurring.Type.MONTH;
                    case "WEEK" -> recurringType = Entry.Recurring.Type.WEEK;
                    case "DAY" -> recurringType = Entry.Recurring.Type.DAY;
                }

                if (recurringType != null) {
                    recurring = new Entry.Recurring(recurringType, recurringFrequency);
                }

                entries.add(new Entry(res.getLong("id"), res.getString("title"), res.getString("info"), start, end, recurring));
            }

            this.close(res);
        } catch (SQLException e) {
            App.get().LOGGER.warning(e.getMessage());
        }

        return entries;
    }

    /**
     * Creates or edits and entry
     * @param entry The entry to create or edit
     */
    public void save(@Nullable Entry entry) {
        if (entry == null) {
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String id = null;
        String title = entry.title();
        String info = entry.info();
        String start = formatter.format(entry.start());
        String end = formatter.format(entry.end());
        String recurringType = "NULL";
        String recurringFrequency = "1";

        if (entry.id() != null) {
            id = Long.toString(entry.id());
        }

        if (entry.recurring() != null) {
            recurringType = entry.recurring().type().toString();
            recurringFrequency = Integer.toString(entry.recurring().frequency());
        }

        String sql;

        if (id == null) {
            sql = "INSERT INTO " + this.table + " (" +
                    "title, info, start, end, recurringType, recurringFrequency" +
                    ") VALUES ('" +
                    title + "', '" +
                    info + "', '" +
                    start + "', '" +
                    end + "', '" +
                    recurringType + "', '" +
                    recurringFrequency + "')";
        } else {
            sql = "UPDATE " + this.table + " SET" +
                    " title = '" + title +
                    "', info = '" + info +
                    "', start = '" + start +
                    "', end = '" + end +
                    "', recurringType = '" + recurringType +
                    "', recurringFrequency = '" + recurringFrequency +
                    "' WHERE" +
                    " id = " + id + ";";
        }
        this.execute(sql);
    }

    /**
     * Deletes an entry
     * @param entry The entry to delete
     */
    public void delete(@Nullable Entry entry) {
        if (entry == null || entry.id() == null) { return; }

        try {
            this.execute("DELETE FROM " + this.table + " WHERE id = '" + entry.id() + "';");
        } catch (Exception e) {
            App.get().LOGGER.warning(e.getMessage());
        }
    }

    /**
     * Connects to a database
     * @return a database connection
     * @throws SQLException if connecting fails
     */
    public @NotNull Connection connect() throws SQLException {
        return DriverManager.getConnection(url + database, user, password);
    }

    /**
     * Checks whether connecting to a database is possible
     * @return {@code true} if a connection could be established
     */
    public boolean valid() {
        try {
            Connection conn = this.connect();
            conn.close();
            return true;
        } catch (SQLException e) {
            App.get().LOGGER.warning(e.getMessage());
            return false;
        }
    }

    /**
     * Executes a sql command
     * @param sql The command to execute
     */
    public boolean execute(@NotNull String sql) {
        try {
            Connection conn = this.connect();
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            stmt.close();
            conn.close();
            return true;
        } catch (SQLException e) {
            App.get().LOGGER.warning(e.getMessage());
            return false;
        }
    }

    /**
     * Executes a sql query
     * @param sql The sql command
     * @return the query results
     */
    public @Nullable ResultSet query(@NotNull String sql) {
        try {
            Connection conn = this.connect();
            Statement stmt = conn.createStatement();
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            App.get().LOGGER.warning(e.getMessage());
        }

        return null;
    }

    /**
     * Closes a result set
     * @param res Result set to close
     */
    public void close(@NotNull ResultSet res) {
        try {
            Statement stmt = res.getStatement();
            Connection conn = stmt.getConnection();
            res.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            App.get().LOGGER.warning(e.getMessage());
        }
    }
}
