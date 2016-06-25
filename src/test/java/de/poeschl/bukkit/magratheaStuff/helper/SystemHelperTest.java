package de.poeschl.bukkit.magratheaStuff.helper;

import de.poeschl.bukkit.magratheaStuff.threads.UpdateCpuLoadTask;
import org.junit.Test;
import org.mockito.Mockito;

import static de.poeschl.bukkit.magratheaStuff.PojoUtilities.$SystemHelper;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * Project: Magrathea-Stuff
 * Created by Markus on 25.06.2016.
 */
public class SystemHelperTest {


    @Test
    public void checkCpuLoad() throws Exception {
        UpdateCpuLoadTask mockTask = Mockito.mock(UpdateCpuLoadTask.class);
        SystemHelper systemHelperToTest = $SystemHelper().withCpuLoadTask(mockTask).build();
        when(mockTask.getLastLoad()).thenReturn(123.4);

        double loadResult = systemHelperToTest.checkCpuLoad();

        assertThat(loadResult).isEqualTo(123.4);
    }
}