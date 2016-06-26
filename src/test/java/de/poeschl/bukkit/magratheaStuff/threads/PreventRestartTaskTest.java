package de.poeschl.bukkit.magratheaStuff.threads;

import de.poeschl.bukkit.magratheaStuff.helper.LogHelper;
import de.poeschl.bukkit.magratheaStuff.helper.SystemHelper;
import de.poeschl.bukkit.magratheaStuff.models.DateTime;
import org.junit.Test;
import org.mockito.Mockito;

import java.text.ParseException;
import java.util.Calendar;
import java.util.logging.Logger;

import static de.poeschl.bukkit.magratheaStuff.PojoUtilities.$PreventRestartTask;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Project: Magrathea-Stuff
 * Created by Markus on 25.06.2016.
 */
public class PreventRestartTaskTest {

    @Test
    public void runActionNecessary() throws ParseException {
        DateTime mockedTime = Mockito.mock(DateTime.class);
        LogHelper mockLogHelper = Mockito.mock(LogHelper.class);
        SystemHelper mockSystemHelper = Mockito.mock(SystemHelper.class);
        PreventRestartTask taskToTest = $PreventRestartTask()
                .withDateTime(mockedTime).withLogHelper(mockLogHelper).withSystemHelper(mockSystemHelper).build();
        when(mockedTime.getTime()).thenReturn(createDateCalendar(12, 34, 56).getTime());
        when(mockLogHelper.getTimeOfLastServerLogEntry()).thenReturn(createDateCalendar(12, 33, 56).getTime());
        when(mockSystemHelper.checkCpuLoad()).thenReturn(100.0);

        taskToTest.run();

        verify(mockLogHelper).printCPULoad(anyInt());
    }

    @Test
    public void runNoActionTime() throws ParseException {
        DateTime mockedTime = Mockito.mock(DateTime.class);
        LogHelper mockLogHelper = Mockito.mock(LogHelper.class);
        SystemHelper mockSystemHelper = Mockito.mock(SystemHelper.class);
        PreventRestartTask taskToTest = $PreventRestartTask()
                .withDateTime(mockedTime).withLogHelper(mockLogHelper).withSystemHelper(mockSystemHelper).build();
        when(mockedTime.getTime()).thenReturn(createDateCalendar(12, 34, 56).getTime());
        when(mockLogHelper.getTimeOfLastServerLogEntry()).thenReturn(createDateCalendar(12, 34, 30).getTime());
        when(mockSystemHelper.checkCpuLoad()).thenReturn(100.0);

        taskToTest.run();

        verify(mockLogHelper, never()).printCPULoad(anyInt());
    }

    @Test
    public void runNoActionLoad() throws ParseException {
        DateTime mockedTime = Mockito.mock(DateTime.class);
        LogHelper mockLogHelper = Mockito.mock(LogHelper.class);
        SystemHelper mockSystemHelper = Mockito.mock(SystemHelper.class);
        PreventRestartTask taskToTest = $PreventRestartTask()
                .withDateTime(mockedTime).withLogHelper(mockLogHelper).withSystemHelper(mockSystemHelper).build();
        when(mockedTime.getTime()).thenReturn(createDateCalendar(12, 34, 56).getTime());
        when(mockLogHelper.getTimeOfLastServerLogEntry()).thenReturn(createDateCalendar(12, 32, 56).getTime());
        when(mockSystemHelper.checkCpuLoad()).thenReturn(50.0);

        taskToTest.run();

        verify(mockLogHelper, never()).printCPULoad(anyInt());
    }

    @Test
    public void runParseException() throws ParseException {
        DateTime mockedTime = Mockito.mock(DateTime.class);
        LogHelper mockLogHelper = Mockito.mock(LogHelper.class);
        SystemHelper mockSystemHelper = Mockito.mock(SystemHelper.class);
        Logger mockLogger = Mockito.mock(Logger.class);
        PreventRestartTask taskToTest = $PreventRestartTask()
                .withDateTime(mockedTime).withLogHelper(mockLogHelper)
                .withSystemHelper(mockSystemHelper).withLogger(mockLogger).build();
        when(mockedTime.getTime()).thenReturn(createDateCalendar(12, 34, 56).getTime());
        when(mockLogHelper.getTimeOfLastServerLogEntry()).thenThrow(new ParseException("", 0));
        when(mockSystemHelper.checkCpuLoad()).thenReturn(100.0);

        taskToTest.run();

        verify(mockLogger).warning(anyString());
    }

    @Test
    public void differenceBetween() {
        PreventRestartTask taskToTest = $PreventRestartTask().build();
        Calendar baseCal = createDateCalendar(12, 34, 56);
        Calendar threeMinBefore = createDateCalendar(12, 31, 56);
        Calendar oneMinuteBefore = createDateCalendar(12, 33, 56);

        long threeMinutesResult = taskToTest.differenceBetween(baseCal.getTime(), threeMinBefore.getTime());
        long oneMinuteBeforeResult = taskToTest.differenceBetween(baseCal.getTime(), oneMinuteBefore.getTime());

        assertThat(threeMinutesResult).isEqualTo(3);
        assertThat(oneMinuteBeforeResult).isEqualTo(1);
    }

    private Calendar createDateCalendar(int hour, int minute, int sec) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, sec);
        return cal;
    }

}