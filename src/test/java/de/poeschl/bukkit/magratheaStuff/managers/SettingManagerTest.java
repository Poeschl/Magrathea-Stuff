package de.poeschl.bukkit.magratheaStuff.managers;

import org.bukkit.configuration.file.FileConfiguration;
import org.junit.Test;
import org.mockito.Mockito;

import static de.poeschl.bukkit.magratheaStuff.PojoUtilities.$SettingsManager;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * Project: Magrathea-Stuff
 * Created by Markus on 25.06.2016.
 */
public class SettingManagerTest {

    @Test
    public void updateConfig() {
        FileConfiguration config1 = Mockito.mock(FileConfiguration.class);
        FileConfiguration config2 = Mockito.mock(FileConfiguration.class);
        SettingManager settingManagerToTest = $SettingsManager().withConfig(config1).build();
        when(config1.getBoolean(SettingManager.HIGH_LOAD_RESTART_PREVENTION_ENABLED)).thenReturn(true);
        when(config2.getBoolean(SettingManager.HIGH_LOAD_RESTART_PREVENTION_ENABLED)).thenReturn(false);

        settingManagerToTest.updateConfig(config2);

        assertThat(settingManagerToTest.getHighLoadRestartPreventionEnabled()).isFalse();
    }

    @Test
    public void getHighLoadRestartPreventionEnabled() {
        FileConfiguration config = Mockito.mock(FileConfiguration.class);
        SettingManager settingManagerToTest = $SettingsManager().withConfig(config).build();
        when(config.getBoolean(SettingManager.HIGH_LOAD_RESTART_PREVENTION_ENABLED)).thenReturn(true);

        boolean result = settingManagerToTest.getHighLoadRestartPreventionEnabled();

        assertThat(result).isTrue();
    }

    @Test
    public void getHighLoadRestartPreventionDisabled() {
        FileConfiguration config = Mockito.mock(FileConfiguration.class);
        SettingManager settingManagerToTest = $SettingsManager().withConfig(config).build();
        when(config.getBoolean(SettingManager.HIGH_LOAD_RESTART_PREVENTION_ENABLED)).thenReturn(false);

        boolean result = settingManagerToTest.getHighLoadRestartPreventionEnabled();

        assertThat(result).isFalse();
    }

}