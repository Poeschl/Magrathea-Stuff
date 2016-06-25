package de.poeschl.bukkit.magratheaStuff.threads;

import net.karneim.pojobuilder.GeneratePojoBuilder;

import javax.management.*;
import java.util.TimerTask;

public class UpdateCpuLoadTask extends TimerTask {

    protected double lastLoad;

    private MBeanServer mBeanServer;
    private Runtime runtime;

    @GeneratePojoBuilder
    public UpdateCpuLoadTask(MBeanServer mBeanServer, Runtime runtime) {
        this.mBeanServer = mBeanServer;
        this.runtime = runtime;
    }

    @Override
    public void run() {
        try {
            ObjectName name = ObjectName.getInstance("java.lang:type=OperatingSystem");
            AttributeList list = mBeanServer.getAttributes(name, new String[]{"ProcessCpuLoad"});

            if (list.isEmpty()) {
                return;
            }

            Attribute att = (Attribute) list.get(0);
            Double value = (Double) att.getValue();

            // usually takes a couple of seconds before we get real values
            if (value == -1.0) {
                return;
            }
            int cores = runtime.availableProcessors();
            // returns a percentage value with 1 decimal point precision
            lastLoad = ((int) (value * 1000) / 10.0) * cores;
        } catch (MalformedObjectNameException | InstanceNotFoundException | ReflectionException e) {
            e.printStackTrace();
        }
    }

    public double getLastLoad() {
        return lastLoad;
    }
}
