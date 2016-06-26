package de.poeschl.bukkit.magratheaStuff.helper;

import de.poeschl.bukkit.magratheaStuff.threads.UpdateCpuLoadTask;
import de.poeschl.bukkit.magratheaStuff.utils.InstanceFactory;
import net.karneim.pojobuilder.GeneratePojoBuilder;

import java.util.Timer;

public class SystemHelper {

    private static final int CPU_CHECK_PERIOD = 10000;

    private Timer cpuLoadTimer;

    private UpdateCpuLoadTask loadTask;

    @GeneratePojoBuilder
    public SystemHelper(UpdateCpuLoadTask cpuLoadTask, InstanceFactory instanceFactory) {
        this.loadTask = cpuLoadTask;
        this.cpuLoadTimer = instanceFactory.createTimer();
    }

    public double checkCpuLoad() {
        return loadTask.getLastLoad();
    }

    public void startCpuLoadTask() {
        cpuLoadTimer.scheduleAtFixedRate(loadTask, 0, CPU_CHECK_PERIOD);
    }
}
