package de.poeschl.bukkit.magratheaStuff.threads;


import de.poeschl.bukkit.magratheaStuff.helper.LogHelper;
import de.poeschl.bukkit.magratheaStuff.helper.SystemHelper;
import de.poeschl.bukkit.magratheaStuff.models.DateTime;
import net.karneim.pojobuilder.GeneratePojoBuilder;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.TimerTask;
import java.util.logging.Logger;

public class PreventRestartTask extends TimerTask {

    private static final int MIN_CPU_TO_PREVENT = 80;
    private static final int MAX_CPU_TO_PREVENT = 150;

    private final DateTime dateTime;

    private LogHelper logHelper;
    private SystemHelper systemHelper;
    private Logger logger;

    @GeneratePojoBuilder
    public PreventRestartTask(Logger logger, LogHelper logHelper, SystemHelper systemHelper, DateTime dateTime) {
        this.logger = logger;
        this.logHelper = logHelper;
        this.systemHelper = systemHelper;
        this.dateTime = dateTime;
        logger.info("PreventRestartThread started");
    }

    @Override
    public void run() {
        try {
            long timeDiff = differenceBetween(dateTime.getTime(), logHelper.getTimeOfLastServerLogEntry());
            double cpuLoad = systemHelper.checkCpuLoad();
            if (timeDiff >= 1 && cpuLoad > MIN_CPU_TO_PREVENT && cpuLoad < MAX_CPU_TO_PREVENT) {
                logHelper.printCPULoad(cpuLoad);
            }
        } catch (ParseException e) {
            logger.warning("Log parse error!");
        }
    }

    /**
     * Compares only the times against each other.
     *
     * @param currentTime
     * @param timeToRun
     * @return difference in minutes
     */
    protected long differenceBetween(Date currentTime, Date timeToRun) {
        Calendar currentCal = Calendar.getInstance();
        currentCal.setTime(currentTime);

        Calendar runCal = Calendar.getInstance();
        runCal.setTime(timeToRun);

        long difference = (currentCal.get(Calendar.HOUR_OF_DAY) - runCal.get(Calendar.HOUR_OF_DAY)) * 60;
        difference += currentCal.get(Calendar.MINUTE) - runCal.get(Calendar.MINUTE);
        difference += (currentCal.get(Calendar.SECOND) - runCal.get(Calendar.SECOND)) / 60;

        return difference;
    }
}
