/**
 * LoggingUtil
 *
 * @author Aaron Weaver  (waaronl@okstate.edu)
 * @version 1.0
 * @since 3/30/15
 */

package main.com.security.anonophant.utils;

/**
 * Created by weava on 3/30/15.
 */
public class LoggingUtil
{
    public static final int LEVEL_ALL = 0;
    public static final int LEVEL_INFO = 1;
    public static final int LEVEL_DEBUG = 2;

    private static final String LOG_TYPE_ALL = "LOG TYPE: ALL:    ";
    private static final String LOG_TYPE_DEBUG = "LOG TYPE: DEBUG:    ";
    private static final String LOG_TYPE_INFO = "LOG TYPE: INFO:   ";

    /**
     * Creates a log with a name, message, and level.
     * @param logName Name of log
     *                Sets log name (Title of log)
     * @param logMessage Message for log
     *                   Sets message for the log created
     * @param logType Type of log
     *                Level of the log for hiding unnecessary logs
     * @return The log itself
     */
    public static void log(String logName, String logMessage, int logType)
    {
        if(logType == LEVEL_ALL)
        {
            System.out.println(LOG_TYPE_ALL + logName + " : " + logMessage);
        }
        else if(logType == LEVEL_INFO)
        {
            System.out.println(LOG_TYPE_INFO + logName + " : " + logMessage);
        }
        else if(logType == LEVEL_DEBUG)
        {
            System.out.println(LOG_TYPE_DEBUG + logName + " : " + logMessage);
        }
        else
        {
            System.out.println(LOG_TYPE_ALL + logName + " : " + logMessage);
        }
    }
}