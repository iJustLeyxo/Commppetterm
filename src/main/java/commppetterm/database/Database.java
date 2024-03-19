package commppetterm.database;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import commppetterm.database.exception.*;
import commppetterm.gui.App;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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
    private @NotNull Settings settings;

    /**
     * Creates the database
     */
    public Database() {
        this.settings = new Settings("jdbc:mysql://sql11.freemysqlhosting.net/", "sql11688847", "calendar", "sql11688847", "RhiGnaQxx1");
    }

    /**
     * Gets the settings
     * @return the setting
     */
    public @NotNull Settings settings() { return this.settings; }

    /**
     * Loads the database settings
     * @throws SettingsException when settings could not be loaded
     */
    public void load() throws SettingsException {
        this.settings = Settings.load();
    }

    /**
     * Updates the database settings
     * @param settings The settings to update to
     */
    public void update(@NotNull Settings settings) {
        this.settings = settings;
    }

    /**
     * Initializes the required tables
     * @throws DatabaseException if initialization failed
     */
    public void init() throws DatabaseException {
        String sql = "CREATE TABLE IF NOT EXISTS " + this.settings.table + " (" +
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
     * @throws DatabaseException if querying entries failed
     */
    public List<Entry> entries(@NotNull LocalDate date) throws DatabaseException { return this.entries(date, date); }

    /**
     * Fetches the entries of a day
     * @param start The first day to fetch the entries of
     * @param end The last day to fetch entries of
     * @return a list of entries
     * @throws DatabaseException if querying entries failed
     */
    public List<Entry> entries(@NotNull LocalDate start, @NotNull LocalDate end) throws DatabaseException {
        DateTimeFormatter queryFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String endPoint = queryFormatter.format(end) + " 23:59:99";
        String startPoint = queryFormatter.format(start) + " 00:00:00";

        ResultSet set = this.query("SELECT * FROM " + this.settings.table + " WHERE start <= '" + endPoint + "' OR end >= '" + startPoint + "' OR recurringType IS NOT NULL;");
        List<Entry> entries = this.parse(set);
        this.close(set);
        return entries;
    }

    /**
     * Creates or edits and entry
     * @param entry The entry to create or edit
     * @throws DatabaseException if saving the entry failed
     */
    public void save(@Nullable Entry entry) throws DatabaseException {
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

        if (entry.recurs()) {
            recurringType = "'" + entry.recurring().type().toString() + "'";
            recurringFrequency = Integer.toString(entry.recurring().frequency());
        }

        String sql;

        if (id == null) {
            sql = "INSERT INTO " + this.settings.table + " (" +
                    "title, info, start, end, recurringType, recurringFrequency" +
                    ") VALUES ('" +
                    title + "', '" +
                    info + "', '" +
                    start + "', '" +
                    end + "', " +
                    recurringType + ", '" +
                    recurringFrequency + "')";
        } else {
            sql = "UPDATE " + this.settings.table + " SET" +
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
     * @throws DatabaseException if deleting the entry failed
     */
    public void delete(@Nullable Entry entry) throws DatabaseException {
        if (entry == null || entry.id() == null) { return; }

        this.execute("DELETE FROM " + this.settings.table + " WHERE id = '" + entry.id() + "';");
    }

    /**
     * Parses a result set
     * @param set The set to parse
     * @return a list of entries from the result set
     * @throws ParseException if parsing the entries failed
     */
    private List<Entry> parse(ResultSet set) throws ParseException {
        try {
            List<Entry> entries = new LinkedList<>();
            DateTimeFormatter resultFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            while (set.next()) {
                LocalDateTime eStart = LocalDateTime.parse(set.getString("start"), resultFormatter);
                LocalDateTime eEnd = LocalDateTime.parse(set.getString("end"), resultFormatter);
                Entry.Recurring recurring = null;
                Entry.Recurring.RecurringType recurringType = null;
                byte recurringFrequency = set.getByte("recurringFrequency");

                if (set.getString("recurringType") != null) {
                    switch (set.getString("recurringType")) {
                        case "YEAR" -> recurringType = Entry.Recurring.RecurringType.YEAR;
                        case "MONTH" -> recurringType = Entry.Recurring.RecurringType.MONTH;
                        case "WEEK" -> recurringType = Entry.Recurring.RecurringType.WEEK;
                        case "DAY" -> recurringType = Entry.Recurring.RecurringType.DAY;
                    }
                }

                if (recurringType != null) {
                    recurring = new Entry.Recurring(recurringType, recurringFrequency);
                }

                entries.add(new Entry(set.getLong("id"), set.getString("title"), set.getString("info"), eStart, eEnd, recurring));
            }

            return entries;
        } catch (SQLException e) {
            throw new ParseException(e);
        }
    }

    /**
     * Connects to a database
     * @return a database connection
     * @throws ConnectException if connecting failed
     */
    public @NotNull Connection connection() throws ConnectException {
        try {
            return DriverManager.getConnection(this.settings.url + this.settings.database, this.settings.user, this.settings.password);
        } catch (SQLException e) {
            throw new ConnectException(e);
        }
    }

    /**
     * Creates a statement from a connection
     * @param connection The connection to create a statement from
     * @return a sql statement
     * @throws StatementCreationException if creating the statement failed
     */
    public @NotNull Statement statement(@NotNull Connection connection) throws StatementCreationException {
        try {
            return connection.createStatement();
        } catch (SQLException e) {
            throw new StatementCreationException(e);
        }
    }

    /**
     * Closes a sql statement's connection
     * @param statement The statement to close
     * @throws StatementCloseException if closing the statement failed
     */
    public void close(@NotNull Statement statement) throws StatementCloseException {
        try {
            statement.getConnection().close();
        } catch (SQLException e) {
            throw new StatementCloseException(e);
        }
    }

    /**
     * Closes a sql result set's connection
     * @param set The result set to close
     * @throws ResultSetCloseException if closing the result set failed
     */
    public void close(@NotNull ResultSet set) throws ResultSetCloseException {
        try {
            set.getStatement().getConnection().close();
        } catch (SQLException e) {
            throw new ResultSetCloseException(e);
        }
    }

    /**
     * Checks whether connecting to a database is possible
     * @throws ConnectionException if connecting failed
     */
    public void test() throws ConnectionException {
        Connection conn = this.connection();

        try {
            conn.close();
        } catch (SQLException e) {
            throw new ConnectionCloseException(e);
        }
    }

    /**
     * Executes a sql command
     * @param sql The command to execute
     * @throws DatabaseException if executing the command failed
     */
    public void execute(@NotNull String sql) throws DatabaseException {
        try {
            Statement stm = this.statement(this.connection());
            stm.execute(sql);
            this.close(stm);
        } catch (SQLException e) {
            throw new ExecutionException(e);
        }
    }

    /**
     * Queries a sql command
     * @param sql The command to query
     * @return the result set of the query
     * @throws DatabaseException if querying failed
     */
    public ResultSet query(@NotNull String sql) throws DatabaseException {
        try {
            Statement stm = this.statement(this.connection());
            return stm.executeQuery(sql);
        } catch (SQLException e) {
            throw new QueryException(e);
        }
    }

    /**
     * Settings data carrier class
     * @param url Database url
     * @param database Database name
     * @param table Entry tables
     * @param user Username
     * @param password User password
     */
    public record Settings (
            @NotNull String url,

            @NotNull String database,

            @NotNull String table,

            @NotNull String user,

            @NotNull String password
    ) {
        /**
         * Settings file name
         */
        public static final @NotNull String FILENAME = "settings.json";

        /**
         * Loads the settings
         * @return a settings object
         * @throws SettingsException if loading the settings failed
         */
        public static @NotNull Settings load() throws SettingsException {
            File file = new File(FILENAME);
            ObjectMapper mapper = new ObjectMapper();

            try {
                return mapper.readValue(file, new TypeReference<>(){});
            } catch (FileNotFoundException e) {
                try {
                    file.createNewFile();
                    App.get().database().settings.save();
                    return App.get().database().settings;
                } catch (IOException ea) {
                    throw new SettingsLoadException(file, ea);
                }
            } catch (IOException eb) {
                throw new SettingsLoadException(file, eb);
            }
        }

        /**
         * Saves the settings
         * @throws SettingsSaveException if saving the settings failed
         */
        public void save() throws SettingsSaveException {
            File file = new File(FILENAME);
            ObjectMapper mapper = new ObjectMapper();

            ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();

            try {
                writer.writeValue(file, this);
            } catch (IOException e) {
                throw new SettingsSaveException(file, e);
            }
        }
    }
}
