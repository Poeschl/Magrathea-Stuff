package de.poeschl.bukkit.magratheaStuff;

import de.poeschl.bukkit.magratheaStuff.helper.LogHelper;
import de.poeschl.bukkit.magratheaStuff.helper.SystemHelper;
import de.poeschl.bukkit.magratheaStuff.managers.SettingManager;
import de.poeschl.bukkit.magratheaStuff.models.DateTime;
import de.poeschl.bukkit.magratheaStuff.threads.PreventRestartTask;
import de.poeschl.bukkit.magratheaStuff.threads.UpdateCpuLoadTask;
import de.poeschl.bukkit.magratheaStuff.utils.InstanceFactory;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Timer;
import java.util.logging.Logger;

import static de.poeschl.bukkit.magratheaStuff.helper.LogHelper.LOGS_FOLDER_NAME;
import static de.poeschl.bukkit.magratheaStuff.helper.LogHelper.LOG_FILE_NAME;

public class MagratheaStuffPlugin extends JavaPlugin {

    private InstanceFactory instanceFactory;
    SettingManager settingManager;
    LogHelper logHelper;
    PreventRestartTask preventRestartTask;
    SystemHelper systemHelper;
    DateTime dateTime;
    Logger logger;
    Timer preventTaskTimer;

    @Override
    public void onEnable() {
        super.onEnable();
        PluginDescriptionFile pdfFile = getInfo();

        if (getConfig().getKeys(false).size() == 0) {
            getConfig();
            saveDefaultConfig();
        }

        instanceFactory = getInstanceFactory();
        logger = instanceFactory.getLogger(this);
        settingManager = instanceFactory.createSettingsManager(getConfig(), logger);
        logHelper = instanceFactory.createLogHelper(logger);
        logHelper.setLogFile(new File("./" + LOGS_FOLDER_NAME + "/" + LOG_FILE_NAME));
        systemHelper = instanceFactory.createSystemHelper(new UpdateCpuLoadTask(instanceFactory.getMBeanServer(), instanceFactory.getRuntime()));
        dateTime = instanceFactory.createDateTime();
        preventRestartTask = instanceFactory.createPreventRestartTask(logger, logHelper, systemHelper, dateTime);
        preventTaskTimer = instanceFactory.createTimer();

        logger.info(pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!");

        systemHelper.startCpuLoadTask();

        initRestartPrevention();
    }

    @Override
    public void onDisable() {
        super.onDisable();
        stopRestartPrevention();
    }

    protected PluginDescriptionFile getInfo() {
        return getDescription();
    }

    protected InstanceFactory getInstanceFactory() {
        return new InstanceFactory();
    }

    protected void initRestartPrevention() {
        if (settingManager.getHighLoadRestartPreventionEnabled()) {
            preventTaskTimer.scheduleAtFixedRate(preventRestartTask, 0, 10000);
        }
    }

    protected void stopRestartPrevention() {
        preventRestartTask.cancel();
    }

    public void reload() {
        reloadConfig();
        settingManager.updateConfig(getConfig());
        stopRestartPrevention();
        initRestartPrevention();
    }
}
