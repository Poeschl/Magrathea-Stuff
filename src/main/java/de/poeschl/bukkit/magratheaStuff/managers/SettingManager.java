package de.poeschl.bukkit.magratheaStuff.managers;

import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.logging.Logger;


public class SettingManager {

    protected static final String HIGH_LOAD_RESTART_PREVENTION_ENABLED = "preventServerFromHighLoadRestart";

    private FileConfiguration config;
    private Logger logger;

    @GeneratePojoBuilder
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
