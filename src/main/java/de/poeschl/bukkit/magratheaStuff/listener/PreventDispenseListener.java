package de.poeschl.bukkit.magratheaStuff.listener;

import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;

import java.util.List;
import java.util.logging.Logger;

/**
 * Project: Magrathea-Stuff
 * Created by Markus on 26.06.2016.
 */
public class PreventDispenseListener implements Listener {

    private Logger logger;
    private List<Material> blockedMaterialsToDispense;

    @GeneratePojoBuilder
    public PreventDispenseListener(Logger logger) {
        this.logger = logger;
    }

    public void setBlockedMaterialsToDispense(List<Material> blockedMaterialsToDispense) {
        this.blockedMaterialsToDispense = blockedMaterialsToDispense;
    }

    @EventHandler
    public void onDispense(BlockDispenseEvent event) {
        if (blockedMaterialsToDispense.contains(event.getItem().getType()) && event.getBlock().getType().equals(Material.DISPENSER)) {
            event.setCancelled(true);
        }
    }
}
