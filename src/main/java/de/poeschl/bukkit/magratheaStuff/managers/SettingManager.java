package de.poeschl.bukkit.magratheaStuff.managers;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.logging.Logger;


public class SettingManager {

    private static final String HIGH_LOAD_RESTART_PREVENTION_ENABLED = "preventServerFromHighLoadRestart";

    private FileConfiguration config;
    private Logger logger;

    public SettingManager(FileConfiguration config, Logger logger) {
        this.config = config;
        this.logger = logger;
    }

    public void updateConfig(FileConfiguration config) {
        this.config = config;
    }

    public boolean getHighLoadRestartPreventionEnabled() {
        return config.getBoolean(HIGH_LOAD_RESTART_PREVENTION_ENABLED);
    }
}
