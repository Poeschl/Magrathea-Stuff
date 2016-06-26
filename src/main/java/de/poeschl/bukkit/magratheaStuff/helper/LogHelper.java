package de.poeschl.bukkit.magratheaStuff.helper;

import net.karneim.pojobuilder.GeneratePojoBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

/**
 * Project: Magrathea-Stuff
 * Created by Markus Pöschl on 03.06.16.
 */
public class LogHelper {

    public static final String LOGS_FOLDER_NAME = "logs";
    public static final String LOG_FILE_NAME = "latest.log";
    private static final DateFormat LOG_DATEFORMAT = new SimpleDateFormat("HH:mm:ss");

    private Logger logger;
    private File logFile;

    @GeneratePojoBuilder
    public LogHelper(Logger logger) {
        this.logger = logger;
    }

    public void setLogFile(File logFile) {
        this.logFile = logFile;
    }

    public Date getTimeOfLastServerLogEntry() throws ParseException {
        String lastLogLine = tail(logFile, 1);
        int startIndex = lastLogLine.indexOf('[');
        int endIndex = lastLogLine.indexOf(']');

        String timeString = lastLogLine.substring(startIndex + 1, endIndex);
        Date time = LOG_DATEFORMAT.parse(timeString);
        return time;
    }

    public void printCPULoad(double load) {
        logger.info("CPU: " + load);
    }

    public void doFakeLogEntry() {
        logger.info(".");
    }

    protected String tail(File file, int lines) {
        java.io.RandomAccessFile fileHandler = null;
        try {
            fileHandler =
                    new RandomAccessFile(file, "r");
            long fileLength = fileHandler.length() - 1;
            StringBuilder sb = new StringBuilder();
            int line = 0;

            for (long filePointer = fileLength; filePointer != -1; filePointer--) {
                fileHandler.seek(filePointer);
                int readByte = fileHandler.readByte();

                if ((char) readByte == '\n') {
                    if (filePointer < fileLength) {
                        line = line + 1;
                    }
                } else if ((char) readByte == '\r') {
                    if (filePointer < fileLength - 1) {
                        line = line + 1;
                    }
                }
                if (line >= lines) {
                    break;
                }
                sb.append((char) readByte);
            }

            String lastLine = sb.reverse().toString();
            return lastLine;
        } catch (FileNotFoundException e) {
            logger.warning("Logging file not found!");
            return null;
        } catch (IOException e) {
            logger.warning("Error on read log file!");
            return null;
        } finally {
            if (fileHandler != null)
                try {
                    fileHandler.close();
                } catch (IOException e) {
                }
        }
    }
}
