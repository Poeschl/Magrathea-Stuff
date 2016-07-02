package de.poeschl.bukkit.magratheaStuff.managers;

import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


public class SettingManager {

    protected static final String HIGH_LOAD_RESTART_PREVENTION_ENABLED = "preventServerFromHighLoadRestart";
    protected static final String BLOCKED_DISPENSE_MATERIAL = "blockedDispenseMaterials";

    private FileConfiguration config;
    private Logger logger;
    private List<Material> cachedBlockList;

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

    public List<Material> getBlockedMaterialsToDispense() {
        if (cachedBlockList == null) {
            List<String> blockedMaterialStrings = config.getStringList(BLOCKED_DISPENSE_MATERIAL);
            cachedBlockList = new ArrayList<>(blockedMaterialStrings.size());
            for (String material : blockedMaterialStrings) {
                cachedBlockList.add(Material.getMaterial(material));
            }
        }
        return cachedBlockList;
    }
}
