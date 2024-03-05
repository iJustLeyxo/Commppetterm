package commppetterm;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.Locale;
import java.util.logging.Logger;

/**
 * Root class for configs
 */
public final class App {
    /**
     * App name
     */
    public static final String name = "Commppetterm";

    /**
     * App version
     */
    public static final String ver = "0.0.0";

    /**
     * Application locale
     */
    public static final Locale locale = Locale.GERMANY;

    /**
     * Global date
     */
    public static @NotNull LocalDate date = LocalDate.now();

    /**
     * Application logger
     */
    public static final Logger logger = Logger.getLogger(App.class.getName());
}