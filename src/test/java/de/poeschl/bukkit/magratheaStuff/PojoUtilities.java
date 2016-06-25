package de.poeschl.bukkit.magratheaStuff;

import de.poeschl.bukkit.magratheaStuff.helper.LogHelperBuilder;
import de.poeschl.bukkit.magratheaStuff.helper.SystemHelperBuilder;
import de.poeschl.bukkit.magratheaStuff.managers.SettingManagerBuilder;
import de.poeschl.bukkit.magratheaStuff.models.DateTime;
import de.poeschl.bukkit.magratheaStuff.threads.PreventRestartTaskBuilder;
import de.poeschl.bukkit.magratheaStuff.threads.UpdateCpuLoadTask;
import de.poeschl.bukkit.magratheaStuff.threads.UpdateCpuLoadTaskBuilder;
import org.bukkit.configuration.file.FileConfiguration;
import org.mockito.Mockito;

import javax.management.MBeanServer;
import java.util.logging.Logger;

/**
 * Project: Magrathea-Stuff
 * Created by Markus on 25.06.2016.
 */
public class PojoUtilities {

    public static LogHelperBuilder $LogHelper() {
        return new LogHelperBuilder()
                .withLogger(Mockito.mock(Logger.class));
    }

    public static SystemHelperBuilder $SystemHelper() {
        return new SystemHelperBuilder()
                .withCpuLoadTask(Mockito.mock(UpdateCpuLoadTask.class));
    }

    public static SettingManagerBuilder $SettingsManager() {
        return new SettingManagerBuilder()
                .withConfig(Mockito.mock(FileConfiguration.class))
                .withLogger(Mockito.mock(Logger.class));
    }

    public static PreventRestartTaskBuilder $PreventRestartTask() {
        return new PreventRestartTaskBuilder()
                .withLogger(Mockito.mock(Logger.class))
                .withLogHelper($LogHelper().build())
                .withSystemHelper($SystemHelper().build())
                .withDateTime(Mockito.mock(DateTime.class));
    }

    public static UpdateCpuLoadTaskBuilder $UpdateCpuLoadTask() {
        return new UpdateCpuLoadTaskBuilder()
                .withMBeanServer(Mockito.mock(MBeanServer.class))
                .withRuntime(Mockito.mock(Runtime.class));
    }
}
