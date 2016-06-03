package de.poeschl.bukkit.magratheaStuff;

import de.poeschl.bukkit.magratheaStuff.helper.LogHelper;
import de.poeschl.bukkit.magratheaStuff.managers.SettingManager;
import de.poeschl.bukkit.magratheaStuff.threads.PreventRestartTask;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Timer;

public class MagratheaStuffPlugin extends JavaPlugin {

    private SettingManager settingManager;
    private LogHelper logHelper;
    private PreventRestartTask preventRestartTask;

    @Override
    public void onEnable() {
        super.onEnable();
        PluginDescriptionFile pdfFile = this.getDescription();

        if (getConfig().getKeys(false).size() == 0) {
            getConfig();
            saveDefaultConfig();
        }
        settingManager = new SettingManager(getConfig(), getLogger());
        logHelper = new LogHelper(this);

        getLogger().info(pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!");

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
        preventRestartTask = new PreventRestartTask(this.getLogger(), logHelper);
        new Timer().scheduleAtFixedRate(preventRestartTask, 1000, 1000);

    }

    private void deactivateRestartPrevention() {
        preventRestartTask.cancel();
    }
}
