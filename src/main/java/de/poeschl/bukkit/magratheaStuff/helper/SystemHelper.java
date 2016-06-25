package de.poeschl.bukkit.magratheaStuff.helper;

import de.poeschl.bukkit.magratheaStuff.threads.UpdateCpuLoadTask;
import net.karneim.pojobuilder.GeneratePojoBuilder;

import java.util.Timer;

public class SystemHelper {

    private static final int CPU_CHECK_PERIOD = 10000;

    private UpdateCpuLoadTask loadTask;

    @GeneratePojoBuilder
    public SystemHelper(UpdateCpuLoadTask cpuLoadTask) {
        this.loadTask = cpuLoadTask;
    }

    public double checkCpuLoad() {
        return loadTask.getLastLoad();
    }

    public void startCpuLoadTask() {
        new Timer().scheduleAtFixedRate(loadTask, 0, CPU_CHECK_PERIOD);
    }
}
