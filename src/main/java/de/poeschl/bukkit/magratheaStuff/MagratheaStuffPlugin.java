package de.poeschl.bukkit.magratheaStuff;

import de.poeschl.bukkit.magratheaStuff.helper.LogHelper;
import de.poeschl.bukkit.magratheaStuff.helper.SystemHelper;
import de.poeschl.bukkit.magratheaStuff.managers.SettingManager;
import de.poeschl.bukkit.magratheaStuff.models.DateTime;
import de.poeschl.bukkit.magratheaStuff.threads.PreventRestartTask;
import de.poeschl.bukkit.magratheaStuff.threads.UpdateCpuLoadTask;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Timer;

public class MagratheaStuffPlugin extends JavaPlugin {

    private SettingManager settingManager;
    private LogHelper logHelper;
    private PreventRestartTask preventRestartTask;
    private SystemHelper systemHelper;
    private DateTime dateTime;

    @Override
    public void onEnable() {
        super.onEnable();
        PluginDescriptionFile pdfFile = this.getDescription();

        if (getConfig().getKeys(false).size() == 0) {
            getConfig();
            saveDefaultConfig();
        }
        settingManager = new SettingManager(getConfig(), getLogger());
        logHelper = new LogHelper(getLogger());
        systemHelper = new SystemHelper(new UpdateCpuLoadTask());
        dateTime = new DateTime();

        getLogger().info(pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!");

        systemHelper.startCpuLoadTask();

        init();
    }

    @Override
    public void onDisable() {
        super.onDisable();
        tearDown();
    }

    private void init() {
        if (settingManager.getHighLoadRestartPreventionEnabled()) {
            activateRestartPrevention();
        }
    }

    private void tearDown() {
        deactivateRestartPrevention();
    }

    public void reload() {
        reloadConfig();
        settingManager.updateConfig(getConfig());
        tearDown();
        init();
    }

    private void activateRestartPrevention() {
        preventRestartTask = new PreventRestartTask(this.getLogger(), logHelper, systemHelper, dateTime);
        new Timer().scheduleAtFixedRate(preventRestartTask, 0, 10000);

    }

    private void deactivateRestartPrevention() {
        preventRestartTask.cancel();
    }
}
