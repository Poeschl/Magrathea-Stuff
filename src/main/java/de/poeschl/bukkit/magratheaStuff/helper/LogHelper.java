package de.poeschl.bukkit.magratheaStuff.helper;

import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Project: Magrathea-Stuff
 * Created by Markus Pöschl on 03.06.16.
 */
public class LogHelper {

    private static final String LOGS_FOLDER_NAME = "logs";
    private static final String LOG_FILE_NAME = "latest.log";
    private static final DateFormat LOG_DATEFORMAT = new SimpleDateFormat("HH:mm:ss");

    private Plugin plugin;
    private File logFile;

    public LogHelper(Plugin plugin) {
        this.plugin = plugin;
        logFile = new File("./" + LOGS_FOLDER_NAME + "/" + LOG_FILE_NAME);
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
        plugin.getLogger().info("CPU: " + load);
    }

    public void doFakeLogEntry() {
        plugin.getLogger().info(".");
    }

    private String tail(File file, int lines) {
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

                if (readByte == 0xA) {
                    if (filePointer < fileLength) {
                        line = line + 1;
                    }
                } else if (readByte == 0xD) {
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
            plugin.getLogger().warning("Logging file not found!");
            return null;
        } catch (IOException e) {
            plugin.getLogger().warning("Error on read log file!");
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
