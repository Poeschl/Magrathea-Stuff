package de.poeschl.bukkit.magratheaStuff.threads;

import org.junit.Test;
import org.mockito.Mockito;

import javax.management.*;
import java.util.ArrayList;
import java.util.List;

import static de.poeschl.bukkit.magratheaStuff.PojoUtilities.$UpdateCpuLoadTask;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * Project: Magrathea-Stuff
 * Created by Markus on 25.06.2016.
 */
public class UpdateCpuLoadTaskTest {

    /**
     * Tests run with 123.4 % load on a 2 core machine
     */
    @Test
    public void run() throws ReflectionException, InstanceNotFoundException {
        List<Attribute> dummyLoadList = new ArrayList<>();
        //The hundred because the system gives it back as percent with 0-1
        dummyLoadList.add(new Attribute("ProcessCpuLoad", 123.4 / 2 / 100));
        MBeanServer mockBeanServer = Mockito.mock(MBeanServer.class);
        Runtime mockRuntime = Mockito.mock(Runtime.class);
        UpdateCpuLoadTask taskToTest = $UpdateCpuLoadTask().withMBeanServer(mockBeanServer).withRuntime(mockRuntime).build();
        when(mockBeanServer.getAttributes(any(ObjectName.class), any(String[].class)))
                .thenReturn(new AttributeList(dummyLoadList));
        when(mockRuntime.availableProcessors()).thenReturn(2);

        taskToTest.run();

        assertThat(taskToTest.getLastLoad()).isEqualTo(123.4);
    }

    @Test
    public void getLastLoad() {
        UpdateCpuLoadTask taskToTest = $UpdateCpuLoadTask().withLastLoad(123.4).build();

        double loadResult = taskToTest.getLastLoad();

        assertThat(loadResult).isEqualTo(123.4);
    }

}