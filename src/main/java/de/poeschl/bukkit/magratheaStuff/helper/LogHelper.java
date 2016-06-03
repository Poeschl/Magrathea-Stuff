package de.poeschl.bukkit.magratheaStuff.helper;

import org.bukkit.plugin.Plugin;

/**
 * Project: Magrathea-Stuff
 * Created by Markus Pöschl on 03.06.16.
 */
public class LogHelper {

    private Plugin plugin;

    public LogHelper(Plugin plugin) {
        this.plugin = plugin;
    }

    public long getTimeOfLastServerLogEntry() {
        //TODO: get last timestamp from log file
        return 0;
    }

    public void doFakeLogEntry() {
        plugin.getLogger().finer(".");
    }
}
