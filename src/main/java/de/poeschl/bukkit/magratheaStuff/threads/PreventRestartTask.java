package de.poeschl.bukkit.magratheaStuff.threads;


import de.poeschl.bukkit.magratheaStuff.helper.LogHelper;

import java.util.Date;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class PreventRestartTask extends TimerTask {

    private LogHelper logHelper;
    private Logger logger;

    public PreventRestartTask(Logger logger, LogHelper logHelper) {
        this.logger = logger;
        this.logHelper = logHelper;
        logger.fine("PreventRestartThread started");
    }

    @Override
    public void run() {
        long timeDiff = new Date().getTime() - logHelper.getTimeOfLastServerLogEntry();

        //TODO: Check for cpu load
        if (TimeUnit.MILLISECONDS.toMinutes(timeDiff) >= 1) {
            logHelper.doFakeLogEntry();
        }


    }
}
