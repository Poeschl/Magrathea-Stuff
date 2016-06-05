package de.poeschl.bukkit.magratheaStuff.threads;

import javax.management.*;
import java.lang.management.ManagementFactory;
import java.util.TimerTask;

public class UpdateCpuLoadTask extends TimerTask {

    private double lastLoad;

    public UpdateCpuLoadTask() {
    }

    @Override
    public void run() {
        try {
            MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
            ObjectName name = ObjectName.getInstance("java.lang:type=OperatingSystem");
            AttributeList list = mbs.getAttributes(name, new String[]{"ProcessCpuLoad"});

            if (list.isEmpty()) {
                return;
            }

            Attribute att = (Attribute) list.get(0);
            Double value = (Double) att.getValue();

            // usually takes a couple of seconds before we get real values
            if (value == -1.0) {
                return;
            }
            int cores = Runtime.getRuntime().availableProcessors();
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
