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
    public void update(@NotNull String url, @NotNull String database, @NotNull String table, @NotNull String user, @NotNull String password) throws SQLException {
        this.url = url;
        this.database = database;
        this.table = table;
        this.user = user;
        this.password = password;
        this.init();
    }

    /**
     * Initializes the required tables
     */
    public void init() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS " + this.table + " (" +
                "id INTEGER AUTO_INCREMENT PRIMARY KEY," +
                "title TEXT NOT NULL," +
                "info TEXT NOT NULL," +
                "start DATETIME NOT NULL," +
                "end DATETIME," +
                "recurringType TEXT CHECK(recurringType IN (null, 'YEAR', 'MONTH', 'WEEK', 'DAY'))," +
                "recurringFrequency INTEGER);";

        this.execute(sql);
    }

    /**
     * Fetches the entries of a day
     * @param date The day to fetch the entries of
     * @return a list of entries
     */
    public List<Entry> entries(@NotNull LocalDate date) throws SQLException { return this.entries(date, date); }

    /**
     * Fetches the entries of a day
     * @param start The first day to fetch the entries of
     * @param end The last day to fetch entries of
     * @return a list of entries
     */
    public List<Entry> entries(@NotNull LocalDate start, @NotNull LocalDate end) throws SQLException {
        List<Entry> entries = new LinkedList<>();

        DateTimeFormatter queryFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String endPoint = queryFormatter.format(end) + " 23:59:99";
        String startPoint = queryFormatter.format(start) + " 00:00:00";

        ResultSet set = this.query("SELECT * FROM " + this.table + " WHERE (start <= '" + endPoint + "' AND end >= '" + startPoint + "') OR recurringType = NULL;");
        entries = this.parse(set);
        this.close(set);
        return entries;
    }

    /**
     * Creates or edits and entry
     * @param entry The entry to create or edit
     */
    public void save(@Nullable Entry entry) throws SQLException {
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

        if (!entry.once()) {
            recurringType = "'" + entry.recurring().type().toString() + "'";
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
                    end + "', " +
                    recurringType + ", '" +
                    recurringFrequency + "')";
        } else {
            sql = "UPDATE " + this.table + " SET" +
                    " title = '" + title +
                    "', info = '" + info +
                    "', start = '" + start +
                    "', end = '" + end +
                    "', recurringType = " + recurringType +
                    ", recurringFrequency = '" + recurringFrequency +
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

    private List<Entry> parse(ResultSet set) throws SQLException {
        List<Entry> entries = new LinkedList<>();
        DateTimeFormatter resultFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        while (set.next()) {
            LocalDateTime eStart = LocalDateTime.parse(set.getString("start"), resultFormatter);
            LocalDateTime eEnd = LocalDateTime.parse(set.getString("end"), resultFormatter);
            Entry.Recurring recurring = null;
            Entry.Recurring.Type recurringType = null;
            byte recurringFrequency = set.getByte("recurringFrequency");

            if (set.getString("recurringType") != null) {
                switch (set.getString("recurringType")) {
                    case "YEAR" -> recurringType = Entry.Recurring.Type.YEAR;
                    case "MONTH" -> recurringType = Entry.Recurring.Type.MONTH;
                    case "WEEK" -> recurringType = Entry.Recurring.Type.WEEK;
                    case "DAY" -> recurringType = Entry.Recurring.Type.DAY;
                }
            }

            if (recurringType != null) {
                recurring = new Entry.Recurring(recurringType, recurringFrequency);
            }

            entries.add(new Entry(set.getLong("id"), set.getString("title"), set.getString("info"), eStart, eEnd, recurring));
        }

        return entries;
    }

    /**
     * Connects to a database
     * @return a database connection
     * @throws SQLException if connecting fails
     */
    public @NotNull Connection connection() throws SQLException {
        return DriverManager.getConnection(url + database, user, password);
    }

    /**
     * Creates a statement from a connection
     * @param connection The connection to create a statement from
     * @return a sql statement
     * @throws SQLException in case creating the statement fails
     */
    public @NotNull Statement statement(@NotNull Connection connection) throws SQLException {
        return connection.createStatement();
    }

    /**
     * Closes a sql statement's connection
     * @param statement The statement to close
     * @throws SQLException if closing the connection failed
     */
    public void close(@NotNull Statement statement) throws SQLException {
        statement.getConnection().close();
    }

    /**
     * Closes a sql result set's connection
     * @param set The result set to close
     * @throws SQLException if closing the result set failed
     */
    public void close(@NotNull ResultSet set) throws SQLException {
        set.getStatement().getConnection().close();
    }

    /**
     * Checks whether connecting to a database is possible
     */
    public void test() throws SQLException {
        Connection conn = this.connection();
        conn.close();
    }

    /**
     * Executes a sql command
     * @param sql The command to execute
     */
    public void execute(@NotNull String sql) throws SQLException {
        Statement stm = this.statement(this.connection());
        stm.execute(sql);
        this.close(stm);
    }

    /**
     * Queries a sql command
     * @param sql The command to query
     * @return the result set of the query
     */
    public ResultSet query(@NotNull String sql) throws SQLException {
        Statement stm = this.statement(this.connection());
        return stm.executeQuery(sql);
    }
}
