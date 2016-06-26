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
import org.junit.Test;
import org.mockito.Mockito;

import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.logging.Logger;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * Project: Magrathea-Stuff
 * Created by Markus on 26.06.2016.
 */
public class MagratheaStuffPluginTest {

    @Test
    public void onEnable() throws Exception {
        //WHEN
        SystemHelper mockSystemHelper = Mockito.mock(SystemHelper.class);
        InstanceFactory mockedInstanceFactory = Mockito.mock(InstanceFactory.class);
        MagratheaStuffPlugin pluginToTest = Mockito.mock(MagratheaStuffPlugin.class);
        FileConfiguration mockedConfig = Mockito.mock(FileConfiguration.class);
        Set<String> dummyKeys = new HashSet<>();
        dummyKeys.add("dummy");

        when(mockedInstanceFactory.createSettingsManager(any(FileConfiguration.class), any(Logger.class))).thenReturn(Mockito.mock(SettingManager.class));
        when(mockedInstanceFactory.createLogHelper(any(Logger.class))).thenReturn(Mockito.mock(LogHelper.class));
        when(mockedInstanceFactory.createSystemHelper(any(UpdateCpuLoadTask.class))).thenReturn(mockSystemHelper);
        when(mockedInstanceFactory.getLogger(any(JavaPlugin.class))).thenReturn(Mockito.mock(Logger.class));
        when(pluginToTest.getConfig()).thenReturn(mockedConfig);
        when(mockedConfig.getKeys(anyBoolean())).thenReturn(dummyKeys);
        when(pluginToTest.getInstanceFactory()).thenReturn(mockedInstanceFactory);
        when(pluginToTest.getInfo()).thenReturn(new PluginDescriptionFile("", "", ""));

        doCallRealMethod().when(pluginToTest).onEnable();

        //THEN
        pluginToTest.onEnable();

        //VERIFY
        verify(mockSystemHelper).startCpuLoadTask();
        verify(pluginToTest).initRestartPrevention();
        verify(pluginToTest, never()).saveDefaultConfig();
    }

    @Test
    public void onEnableFirstTime() throws Exception {
        //WHEN
        SystemHelper mockSystemHelper = Mockito.mock(SystemHelper.class);
        InstanceFactory mockedInstanceFactory = Mockito.mock(InstanceFactory.class);
        MagratheaStuffPlugin pluginToTest = Mockito.mock(MagratheaStuffPlugin.class);

        when(mockedInstanceFactory.createSettingsManager(any(FileConfiguration.class), any(Logger.class))).thenReturn(Mockito.mock(SettingManager.class));
        when(mockedInstanceFactory.createLogHelper(any(Logger.class))).thenReturn(Mockito.mock(LogHelper.class));
        when(mockedInstanceFactory.createSystemHelper(any(UpdateCpuLoadTask.class))).thenReturn(mockSystemHelper);
        when(mockedInstanceFactory.getLogger(any(JavaPlugin.class))).thenReturn(Mockito.mock(Logger.class));
        when(pluginToTest.getConfig()).thenReturn(Mockito.mock(FileConfiguration.class));
        when(pluginToTest.getInstanceFactory()).thenReturn(mockedInstanceFactory);
        when(pluginToTest.getInfo()).thenReturn(new PluginDescriptionFile("", "", ""));

        doCallRealMethod().when(pluginToTest).onEnable();

        //THEN
        pluginToTest.onEnable();

        //VERIFY
        verify(mockSystemHelper).startCpuLoadTask();
        verify(pluginToTest).initRestartPrevention();
        verify(pluginToTest).saveDefaultConfig();
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
        //WHEN
        SettingManager mockSettings = Mockito.mock(SettingManager.class);
        Timer preventMockTimer = Mockito.mock(Timer.class);
        PreventRestartTask mockTask = Mockito.mock(PreventRestartTask.class);
        MagratheaStuffPlugin pluginToTest = Mockito.mock(MagratheaStuffPlugin.class);
        pluginToTest.settingManager = mockSettings;
        pluginToTest.preventRestartTask = mockTask;
        pluginToTest.preventTaskTimer = preventMockTimer;

        when(mockSettings.getHighLoadRestartPreventionEnabled()).thenReturn(true);

        doCallRealMethod().when(pluginToTest).initRestartPrevention();

        //THEN
        pluginToTest.initRestartPrevention();

        //VERIFY
        verify(preventMockTimer).scheduleAtFixedRate(eq(mockTask), eq(0L), anyLong());
    }

    @Test
    public void initRestartPreventionNegative() {
        //WHEN
        SettingManager mockSettings = Mockito.mock(SettingManager.class);
        Timer preventMockTimer = Mockito.mock(Timer.class);
        PreventRestartTask mockTask = Mockito.mock(PreventRestartTask.class);
        MagratheaStuffPlugin pluginToTest = Mockito.mock(MagratheaStuffPlugin.class);
        pluginToTest.settingManager = mockSettings;
        pluginToTest.preventRestartTask = mockTask;
        pluginToTest.preventTaskTimer = preventMockTimer;

        when(mockSettings.getHighLoadRestartPreventionEnabled()).thenReturn(false);

        doCallRealMethod().when(pluginToTest).initRestartPrevention();

        //THEN
        pluginToTest.initRestartPrevention();

        //VERIFY
        verify(preventMockTimer, never()).scheduleAtFixedRate(eq(mockTask), anyLong(), anyLong());
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