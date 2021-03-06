package de.poeschl.bukkit.magratheaStuff.utils;

import de.poeschl.bukkit.magratheaStuff.helper.LogHelper;
import de.poeschl.bukkit.magratheaStuff.helper.SystemHelper;
import de.poeschl.bukkit.magratheaStuff.listener.PreventDispenseListener;
import de.poeschl.bukkit.magratheaStuff.managers.SettingManager;
import de.poeschl.bukkit.magratheaStuff.models.DateTime;
import de.poeschl.bukkit.magratheaStuff.threads.PreventRestartTask;
import de.poeschl.bukkit.magratheaStuff.threads.UpdateCpuLoadTask;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import javax.management.MBeanServer;
import java.lang.management.ManagementFactory;
import java.util.Timer;
import java.util.logging.Logger;

/**
 * Project: Magrathea-Stuff
 * Created by Markus on 26.06.2016.
 */
public class InstanceFactory {

    public SettingManager createSettingsManager(FileConfiguration config, Logger logger) {
        return new SettingManager(config, logger);
    }

    public LogHelper createLogHelper(Logger logger) {
        return new LogHelper(logger);
    }

    public SystemHelper createSystemHelper(UpdateCpuLoadTask updateCpuLoadTask) {
        return new SystemHelper(updateCpuLoadTask, this);
    }

    public DateTime createDateTime() {
        return new DateTime();
    }

    public MBeanServer getMBeanServer() {
        return ManagementFactory.getPlatformMBeanServer();
    }

    public Runtime getRuntime() {
        return Runtime.getRuntime();
    }

    public Logger getLogger(JavaPlugin javaPlugin) {
        return javaPlugin.getLogger();
    }

    public Timer createTimer() {
        return new Timer();
    }

    public PreventRestartTask createPreventRestartTask(Logger logger, LogHelper logHelper, SystemHelper systemHelper, DateTime dateTime) {
        return new PreventRestartTask(logger, logHelper, systemHelper, dateTime);
    }

    public PreventDispenseListener createPreventDispenseListener(Logger logger) {
        return new PreventDispenseListener(logger);
    }
}
