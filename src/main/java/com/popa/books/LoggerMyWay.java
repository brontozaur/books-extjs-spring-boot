/**
 * This is an implementation of Log4J logging system, accepting txt, XML and HTML formats for the resulted log files and able to add or not a console logger, useful in development stage or other
 * technical/debug stages.
 */
package com.popa.books;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Properties;

import javax.swing.JOptionPane;

import org.apache.log4j.*;

public final class LoggerMyWay {

    public static final int LOG_TXT = 0;
    public static final int LOG_XML = 1;
    public static final int LOG_HTML = 2;

    public static final String LOG4J_APP_STARTUP_DIR = "startup";
    public static final String LOG4J_ACCOUNT_LOGS_DIR = "logs";
    public static final String LOG4J_ACCOUNTS_DIR = "accounts";

    private static final String PROP_ROOT_LOGGER = "log4j.rootLogger";
    private static final String PROP_LOGGER_LEVEL = "INFO";
    private static final String PROP_LOGGER_ERROR = "Cannot create new dir..check if u have enough rights on the target";

    private static final String PROP_CONSOLE_APPENDER_NAME = "log4j.appender.myConsole";
    private static final String PROP_CONSOLE_APPENDER_LAYOUT = "log4j.appender.myConsole.layout";
    private static final String PROP_CONSOLE_APPENDER_LAYOUT_CONVERSION_PATTERN = "log4j.appender.myConsole.layout.ConversionPattern";
    private static final String PROP_CONSOLE_APPENDER_IMMEDIATE_FLUSH = "log4j.appender.myConsole.ImmediateFlush";

    private static final String PROP_ROLLING_APPENDER_FILE = "log4j.appender.myRollingLog.File";
    private static final String PROP_ROLLING_APPENDER_NAME = "log4j.appender.myRollingLog";
    private static final String PROP_ROLLING_APPENDER_LAYOUT = "log4j.appender.myRollingLog.layout";
    private static final String PROP_ROLLING_APPENDER_LAYOUT_CONVERSION_PATTERN = "log4j.appender.myRollingLog.layout.ConversionPattern";
    private static final String PROP_ROLLING_APPENDER_MAX_BACKUP_INDEX = "log4j.appender.myRollingLog.MaxBackupIndex";
    private static final String PROP_ROLLING_APPENDER_MAX_FILE_SIZE = "log4j.appender.myRollingLog.MaxFileSize";
    private static final String PROP_ROLLING_APPENDER_THRESHOLD = "log4j.appender.myRollingLog.threshold";

    /**
     * this value should be used for XMLLayout and/or HTMLLayout only!
     */
    private static final String PROP_ROLLING_APPENDER_LOCATION_INFO = "log4j.appender.myRollingLog.layout.LocationInfo";
    /**
     * this value should be used for HTMLLayout only!
     */
    private static final String PROP_ROLLING_APPENDER_HTML_TITLE = "log4j.appender.myRollingLog.layout.Title";

    public static String applicationLog;

    private LoggerMyWay() {
    }

    public static void configure(final int type, final String userName, final boolean debug) {
        Calendar calendar;
        Properties props;
        try {
            /**
             * determinam calea fisierului curent de loguri, extensia si numele acestuia.
             */
            calendar = Calendar.getInstance();
            final int year = calendar.get(Calendar.YEAR);
            final int month = calendar.get(Calendar.MONTH) + 1;

            final int day = calendar.get(Calendar.DAY_OF_MONTH);
            File file;
            if ((userName == null) || (userName.length() == 0)) {
                LoggerMyWay.applicationLog = System.getProperties().getProperty("user.home") + File.separator + LoggerMyWay.LOG4J_APP_STARTUP_DIR;
                file = new File(LoggerMyWay.applicationLog);
                if (!file.exists() && !file.mkdir()) {
                    throw new IOException(LoggerMyWay.PROP_LOGGER_ERROR);
                }

            } else {
                LoggerMyWay.applicationLog = System.getProperties().getProperty("user.home") + File.separator + LoggerMyWay.LOG4J_ACCOUNTS_DIR;
                file = new File(LoggerMyWay.applicationLog);
                if (!file.exists() && !file.mkdir()) {
                    throw new IOException(LoggerMyWay.PROP_LOGGER_ERROR);
                }
                LoggerMyWay.applicationLog += File.separator + userName;
                file = new File(LoggerMyWay.applicationLog);
                if (!file.exists() && !file.mkdir()) {
                    throw new IOException(LoggerMyWay.PROP_LOGGER_ERROR);
                }
                LoggerMyWay.applicationLog += File.separator + LoggerMyWay.LOG4J_ACCOUNT_LOGS_DIR;
                file = new File(LoggerMyWay.applicationLog);
                if (!file.exists() && !file.mkdir()) {
                    throw new IOException(LoggerMyWay.PROP_LOGGER_ERROR);
                }
            }
            LoggerMyWay.applicationLog += File.separator + year;
            file = new File(LoggerMyWay.applicationLog);
            if (!file.exists() && !file.mkdir()) {
                throw new IOException(LoggerMyWay.PROP_LOGGER_ERROR);
            }
            LoggerMyWay.applicationLog += File.separator + (month > 9 ? String.valueOf(month) : ("0" + month));
            file = new File(LoggerMyWay.applicationLog);
            if (!file.exists() && !file.mkdir()) {
                throw new IOException(LoggerMyWay.PROP_LOGGER_ERROR);
            }
            LoggerMyWay.applicationLog += File.separator + (day > 9 ? String.valueOf(day) : ("0" + day));
            file = new File(LoggerMyWay.applicationLog);
            if (!file.exists() && !file.mkdir()) {
                throw new IOException(LoggerMyWay.PROP_LOGGER_ERROR);
            }
            LoggerMyWay.applicationLog += File.separator + calendar.getTime().getTime();
            /**
             * initializam o colectie Properties, cu setarile loger-ului.
             */
            props = new Properties();
            /**
             * daca suntem in faza de debug/dezvoltare, atasam un appender pt afisarea in consola a log-urilor.
             */
            if (debug) {
                props.put(LoggerMyWay.PROP_ROOT_LOGGER, "INFO, myConsole, myRollingLog");
                props.put(LoggerMyWay.PROP_CONSOLE_APPENDER_NAME, "org.apache.log4j.ConsoleAppender");
                props.put(LoggerMyWay.PROP_CONSOLE_APPENDER_LAYOUT, "org.apache.log4j.PatternLayout");
                props.put(LoggerMyWay.PROP_CONSOLE_APPENDER_LAYOUT_CONVERSION_PATTERN, "%-5p [%d{HH:mm:ss,SSS}] [%l] # %m%n");
                props.put(LoggerMyWay.PROP_CONSOLE_APPENDER_IMMEDIATE_FLUSH, String.valueOf(true));
            } else {
                props.put(LoggerMyWay.PROP_ROOT_LOGGER, "INFO, myRollingLog");
            }

            LoggerMyWay.initProps(props, type);

            props.put(LoggerMyWay.PROP_ROLLING_APPENDER_FILE, LoggerMyWay.applicationLog);
            System.getProperties().put("current.log.file", LoggerMyWay.applicationLog);
            BasicConfigurator.resetConfiguration();
            PropertyConfigurator.configure(props);
        } catch (IOException exc) {
            JOptionPane.showMessageDialog(null, "Aplicatia nu poate initializa sistemul de loguri."
                    + "Este posibil sa nu aveti drept de scriere la adresa specificata. \nAplicatia se va inchide acum.", "Mesaj de eroare",
                    JOptionPane.ERROR_MESSAGE);
            LoggerMyWay.shutDown();
            BasicConfigurator.configure();
            Logger.getLogger(LoggerMyWay.class).error(exc, exc);
        }
    }

    private static void initProps(final Properties props, final int type) {
        /**
         * setam proprietatile pentru RollingLog, un log care scrie in fisiere multiple, intr-o singura instanta, cand aceste fisiere ating dimensiunea maxima
         * specificata.
         */
        switch (type) {
        case LOG_TXT: {
            LoggerMyWay.applicationLog += ".log";
            props.put(LoggerMyWay.PROP_ROLLING_APPENDER_NAME, "org.apache.log4j.RollingFileAppender");
            props.put(LoggerMyWay.PROP_ROLLING_APPENDER_MAX_BACKUP_INDEX, "25");
            props.put(LoggerMyWay.PROP_ROLLING_APPENDER_MAX_FILE_SIZE, "10240KB");
            props.put(LoggerMyWay.PROP_ROLLING_APPENDER_THRESHOLD, LoggerMyWay.PROP_LOGGER_LEVEL);
            props.put(LoggerMyWay.PROP_ROLLING_APPENDER_LAYOUT, "org.apache.log4j.PatternLayout");
            props.put(LoggerMyWay.PROP_ROLLING_APPENDER_LAYOUT_CONVERSION_PATTERN, "%-5p [%d{HH:mm:ss,SSS}] [%l] # %m%n");
            break;
        }
        case LOG_HTML: {
            LoggerMyWay.applicationLog += ".html";
            props.put(LoggerMyWay.PROP_ROLLING_APPENDER_NAME, "org.apache.log4j.RollingFileAppender");
            props.put(LoggerMyWay.PROP_ROLLING_APPENDER_MAX_BACKUP_INDEX, "25");
            props.put(LoggerMyWay.PROP_ROLLING_APPENDER_MAX_FILE_SIZE, "10240KB");
            props.put(LoggerMyWay.PROP_ROLLING_APPENDER_THRESHOLD, LoggerMyWay.PROP_LOGGER_LEVEL);
            props.put(LoggerMyWay.PROP_ROLLING_APPENDER_LAYOUT, "org.apache.log4j.HTMLLayout");
            props.put(LoggerMyWay.PROP_ROLLING_APPENDER_HTML_TITLE, "UF HTML Log");
            props.put(LoggerMyWay.PROP_ROLLING_APPENDER_LOCATION_INFO, String.valueOf(true));
            break;
        }
        case LOG_XML: {
            LoggerMyWay.applicationLog += ".xml";
            props.put(LoggerMyWay.PROP_ROLLING_APPENDER_NAME, "org.apache.log4j.FileAppender");
            props.put(LoggerMyWay.PROP_ROLLING_APPENDER_THRESHOLD, LoggerMyWay.PROP_LOGGER_LEVEL);
            props.put(LoggerMyWay.PROP_ROLLING_APPENDER_LAYOUT, "org.apache.log4j.xml.XMLLayout");
            props.put(LoggerMyWay.PROP_ROLLING_APPENDER_LOCATION_INFO, String.valueOf(true));
            break;
        }
        default: {
            props.put(LoggerMyWay.PROP_ROLLING_APPENDER_NAME, "org.apache.log4j.RollingFileAppender");
            props.put(LoggerMyWay.PROP_ROLLING_APPENDER_MAX_BACKUP_INDEX, "25");
            props.put(LoggerMyWay.PROP_ROLLING_APPENDER_MAX_FILE_SIZE, "10240KB");
            props.put(LoggerMyWay.PROP_ROLLING_APPENDER_THRESHOLD, LoggerMyWay.PROP_LOGGER_LEVEL);
            props.put(LoggerMyWay.PROP_ROLLING_APPENDER_LAYOUT, "org.apache.log4j.PatternLayout");
            props.put(LoggerMyWay.PROP_ROLLING_APPENDER_LAYOUT_CONVERSION_PATTERN, "%-5p [%d{HH:mm:ss,SSS}] [%l] # %m%n");
        }
        }
    }

    /**
     * not sure, but i guess something like that should be used when a hotswap (app running) is performed, in order to change the type of the current log file
     * format.
     */
    public static void shutDown() {
        LogManager.shutdown();
    }
}
