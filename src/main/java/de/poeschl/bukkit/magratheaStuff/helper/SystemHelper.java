package de.poeschl.bukkit.magratheaStuff.helper;

import de.poeschl.bukkit.magratheaStuff.threads.UpdateCpuLoadTask;
import org.bukkit.plugin.Plugin;

import java.util.Timer;

public class SystemHelper {

    private static final int CPU_CHECK_PERIOD = 10000;

    private Plugin plugin;
    private UpdateCpuLoadTask loadTask;

    public SystemHelper(Plugin plugin) {
        this.plugin = plugin;
        this.loadTask = new UpdateCpuLoadTask();
    }

    public double checkCpuLoad() {
        return loadTask.getLastLoad();
    }

    public void startCpuLoadTask() {
        new Timer().scheduleAtFixedRate(loadTask, 0, CPU_CHECK_PERIOD);
    }
}
