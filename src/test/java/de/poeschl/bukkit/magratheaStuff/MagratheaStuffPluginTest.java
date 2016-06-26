package de.poeschl.bukkit.magratheaStuff;

import de.poeschl.bukkit.magratheaStuff.helper.LogHelper;
import de.poeschl.bukkit.magratheaStuff.helper.SystemHelper;
import de.poeschl.bukkit.magratheaStuff.managers.SettingManager;
import de.poeschl.bukkit.magratheaStuff.threads.PreventRestartTask;
import de.poeschl.bukkit.magratheaStuff.threads.UpdateCpuLoadTask;
import de.poeschl.bukkit.magratheaStuff.utils.InstanceFactory;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.lang.reflect.Method;
import java.util.logging.Logger;

import static junit.framework.TestCase.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.whenNew;

/**
 * Project: Magrathea-Stuff
 * Created by Markus on 26.06.2016.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({MagratheaStuffPlugin.class, JavaPlugin.class})
public class MagratheaStuffPluginTest {


    //TODO: somehow mock the description and then test also the first start!
    @Ignore
    @Test
    public void onEnable() throws Exception {
        //WHEN
        SettingManager mockSettings = Mockito.mock(SettingManager.class);
        LogHelper mockLogHelper = Mockito.mock(LogHelper.class);
        SystemHelper mockSystemHelper = Mockito.mock(SystemHelper.class);
        InstanceFactory mockedInstanceFactory = Mockito.mock(InstanceFactory.class);
        MagratheaStuffPlugin pluginToTest = Mockito.mock(MagratheaStuffPlugin.class);

        when(mockedInstanceFactory.createSettingsManager(any(FileConfiguration.class), any(Logger.class))).thenReturn(mockSettings);
        when(mockedInstanceFactory.createLogHelper(any(Logger.class))).thenReturn(mockLogHelper);
        when(mockedInstanceFactory.createSystemHelper(any(UpdateCpuLoadTask.class))).thenReturn(mockSystemHelper);
        when(mockedInstanceFactory.getLogger(any(JavaPlugin.class))).thenReturn(Mockito.mock(Logger.class));
        whenNew(InstanceFactory.class).withAnyArguments().thenReturn(mockedInstanceFactory);
        when(pluginToTest.getConfig()).thenReturn(Mockito.mock(FileConfiguration.class));
//        when(pluginToTest.getDescription()).thenReturn(Mockito.mock(PluginDescriptionFile.class));
        Method descGetter = PowerMockito.method(JavaPlugin.class, "getDescription");
        PowerMockito.when(JavaPlugin.class, descGetter).withNoArguments().thenReturn(Mockito.mock(PluginDescriptionFile.class));

        doCallRealMethod().when(pluginToTest).onEnable();

        //THEN
        pluginToTest.onEnable();

        //VERIFY
        verify(mockSystemHelper).startCpuLoadTask();
        verify(pluginToTest).initRestartPrevention();
    }

    @Test
    public void onDisable() {
        MagratheaStuffPlugin pluginToTest = Mockito.mock(MagratheaStuffPlugin.class);
        doCallRealMethod().when(pluginToTest).onDisable();

        pluginToTest.onDisable();

        verify(pluginToTest).stopRestartPrevention();
    }

    @Test
    public void initRestartPreventionPositive() {
        fail();
    }

    @Test
    public void initRestartPreventionNegative() {
        fail();
    }

    @Test
    public void stopRestartPrevention() {
        //WHEN
        PreventRestartTask mockTask = Mockito.mock(PreventRestartTask.class);
        MagratheaStuffPlugin pluginToTest = Mockito.mock(MagratheaStuffPlugin.class);
        pluginToTest.preventRestartTask = mockTask;

        doCallRealMethod().when(pluginToTest).stopRestartPrevention();

        //THEN
        pluginToTest.stopRestartPrevention();

        //VERIFY
        verify(mockTask).cancel();

    }

    @Test
    public void reload() throws Exception {
        //WHEN
        SettingManager mockSettings = Mockito.mock(SettingManager.class);
        MagratheaStuffPlugin pluginToTest = Mockito.mock(MagratheaStuffPlugin.class);
        pluginToTest.settingManager = mockSettings;

        when(pluginToTest.getConfig()).thenReturn(Mockito.mock(FileConfiguration.class));

        doCallRealMethod().when(pluginToTest).reload();

        //THEN
        pluginToTest.reload();

        //VERIFY
        verify(pluginToTest).reloadConfig();
        verify(pluginToTest).stopRestartPrevention();
        verify(pluginToTest).initRestartPrevention();
        verify(mockSettings).updateConfig(any(FileConfiguration.class));
    }
}