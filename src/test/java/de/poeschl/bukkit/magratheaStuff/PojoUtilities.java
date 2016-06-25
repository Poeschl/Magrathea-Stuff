package de.poeschl.bukkit.magratheaStuff;

import de.poeschl.bukkit.magratheaStuff.helper.LogHelperBuilder;
import de.poeschl.bukkit.magratheaStuff.helper.SystemHelperBuilder;
import de.poeschl.bukkit.magratheaStuff.managers.SettingManagerBuilder;
import de.poeschl.bukkit.magratheaStuff.threads.UpdateCpuLoadTask;
import org.bukkit.configuration.file.FileConfiguration;
import org.mockito.Mockito;

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
}
