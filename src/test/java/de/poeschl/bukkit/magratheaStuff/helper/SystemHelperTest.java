package de.poeschl.bukkit.magratheaStuff.helper;

import de.poeschl.bukkit.magratheaStuff.threads.UpdateCpuLoadTask;
import de.poeschl.bukkit.magratheaStuff.utils.InstanceFactory;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Timer;

import static de.poeschl.bukkit.magratheaStuff.PojoUtilities.$SystemHelper;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
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

    @Test
    public void startCpuLoadTask() {
        Timer mockTimer = Mockito.mock(Timer.class);
        UpdateCpuLoadTask mockTask = Mockito.mock(UpdateCpuLoadTask.class);
        InstanceFactory mockInstances = Mockito.mock(InstanceFactory.class);
        when(mockInstances.createTimer()).thenReturn(mockTimer);
        SystemHelper systemHelperToTest = $SystemHelper().withCpuLoadTask(mockTask).withInstanceFactory(mockInstances).build();

        systemHelperToTest.startCpuLoadTask();

        verify(mockTimer).scheduleAtFixedRate(eq(mockTask), eq(0L), anyLong());
    }
}