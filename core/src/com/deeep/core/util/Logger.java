package com.deeep.core.util;

import java.io.File;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * User: elmar
 * Date: 4/1/13
 * Time: 8:30 PM
 * Packate: com.deeep.sod2.utility
 * Project: SoD2
 */
public class Logger {

    /**
     * The SYSTEM log level. <br>
     * Basic engine status messages like engine startup, player
     * connected/disconnected, starting/finishing loading resources and so on.
     */
    public static final int SYSTEM = 0x00;
    /**
     * The ERROR log level. <br>
     * Critical errors that caused an engine shutdown.<br>
     * <br>
     * ALWAYS log an error message first explaining what went wrong and then the
     * exception.
     */
    public static final int ERROR = 0x01;
    /**
     * The WARN log level. Warnings (Exceptions) <br>
     * Non critical exceptions that don't cause an engine shutdown but shouldn't
     * have happened at all. <br>
     * <br>
     * ALWAYS log an warn message first explaining what went wrong and then the
     * exception.
     */
    public static final int WARN = 0x02;
    /**
     * The DEBUG log level. <br>
     * Used to debug stuff during development.<br>
     * Do whatever you want but try to log self-explaining messages.
     */
    public static final int DEBUG = 0x03;
    /**
     * The ALL log level.<br>
     * Theres no method for this. Its just used to set the Log Level in the
     * configuration so that everything is logged.
     */
    public static final int ALL = 0xFF;
    public static int loglevel = Logger.ALL;
    public static String newline = System.getProperty("line.separator");
    /** The singleton instance of Logger. */
    private static Logger instance;
    /** The print writer. */
    private PrintWriter printWriter;
    /** The log file. */
    private File logFile;
    /** The date format. */
    private DateFormat logDateFormat;

    /** Instantiates a new logger. */
    public Logger() {
        logDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        printWriter = new PrintWriter(System.out);
        logFile = new File("log.txt");

        if (false) {

            if (!logFile.isDirectory()) {

                try {
                    logFile.delete();
                    logFile.createNewFile();
                    printWriter = new PrintWriter(logFile);
                } catch (Exception e) {
                    writeMessage(this.getClass(), "Unable to create log file. Logging to System.out instead,", WARN);
                }
            }
        }
        writeMessage(this.getClass(), "Logger initalized", SYSTEM);
    }

    /**
     * Gets the single instance of Logger.
     *
     * @return single instance of Logger
     */
    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    /**
     * Write a message to the log.
     *
     * @param message  the message
     * @param logLevel the log level
     */
    private synchronized void writeMessage(Class aClass, String message, int logLevel) {
        if (printWriter != null && logLevel <= loglevel) {
            String append = (aClass != null) ? " \"" + aClass.getSimpleName() + "\"" : " enum ";
            printWriter.write(getLogPrefix(logLevel) + message);
            printWriter.write(append);
            printWriter.write(newline);
            printWriter.flush();
        }
    }

    /**
     * Write an exception to the log.
     *
     * @param exception the exception
     * @param logLevel  the log level
     */
    private synchronized void writeException(Class aClass, Exception exception, int logLevel) {
        if (printWriter != null && logLevel <= loglevel) {
            printWriter.write(getLogPrefix(logLevel) + getStackTraceString(exception));
            String append = (aClass != null) ? " \"" + aClass.getSimpleName() + "\"" : " enum ";
            printWriter.write(append);
            printWriter.write(newline);
            printWriter.flush();
        }
    }

    /**
     * Gets the log prefix containing the formatted date, the log level and the
     * error message.
     *
     * @param logLevel the log level
     * @return the log prefix
     */
    private String getLogPrefix(int logLevel) {
        StringBuilder sb = new StringBuilder();
        sb.append(logDateFormat.format(new Date()));
        switch (logLevel) {
            case ERROR:
                sb.append(" - ERROR: ");
                break;
            case WARN:
                sb.append(" - WARN:  ");
                break;
            case DEBUG:
                sb.append(" - DEBUG:  ");
                break;
            case SYSTEM:
                sb.append(" - SYSTEM: ");
        }
        return sb.toString();
    }

    /**
     * Create a string representation of the passed {@link Exception}.
     *
     * @param exception the exception
     * @return the exception and stack trace as string
     */
    public String getStackTraceString(Exception exception) {
        StringBuilder sb = new StringBuilder();
        sb.append(exception.getMessage());
        for (StackTraceElement sTE : exception.getStackTrace()) {
            sb.append("\n");
            sb.append(sTE.toString());
        }
        return sb.toString();
    }

    /** Close the printwriter. */
    public void close() {
        printWriter.close();
    }

    /**
     * Log a message on the ERROR level.
     *
     * @param message the message
     */
    public void error(Class aClass, Object message) {
        writeMessage(aClass, message.toString(), ERROR);
    }

    /**
     * Log an exception on the ERROR level.
     *
     * @param exception the exception
     */
    public void error(Class aClass, Exception exception) {
        writeException(aClass, exception, ERROR);
    }

    /**
     * Log a message on the WARN level.
     *
     * @param message the message
     */
    public void warn(Class aClass, Object message) {
        writeMessage(aClass, message.toString(), WARN);
    }

    /**
     * Log an exception on the WARN level.
     *
     * @param exception the exception
     */
    public void warn(Class aClass, Exception exception) {
        writeException(aClass, exception, WARN);
    }

    /**
     * Log a message on the DEBUG level.
     *
     * @param message the message
     */
    public void debug(Class aClass, Object message) {
        writeMessage(aClass, message.toString(), DEBUG);
    }

    /**
     * Log an exception on the DEBUG level.
     *
     * @param exception the exception
     */
    public void debug(Class aClass, Exception exception) {
        writeException(aClass, exception, DEBUG);
    }

    /**
     * Log a message on the SYSTEM level.
     *
     * @param message the message
     */
    public void system(Class aClass, Object message) {
        writeMessage(aClass, message.toString(), SYSTEM);
    }

    /**
     * Log an exception on the SYSTEM level.
     *
     * @param exception the exception
     */
    public void system(Class aClass, Exception exception) {
        writeException(aClass, exception, SYSTEM);
    }

}
