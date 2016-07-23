package de.poeschl.bukkit.magratheaStuff.helper;

import org.junit.Test;
import org.mockito.Mockito;

import java.io.File;
import java.net.URL;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

import static de.poeschl.bukkit.magratheaStuff.PojoUtilities.$LogHelper;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;

public class LogHelperTest {

    private static final URL TEST_LOG_URL = Thread.currentThread().getContextClassLoader().getResource("tailtest.log");
    private static final URL TEST_LOG_URL_MAC = Thread.currentThread().getContextClassLoader().getResource("tailtest-mac.log");

    @Test
    public void getTimeOfLastServerLogEntry() throws ParseException {
        File testLog = new File(TEST_LOG_URL.getPath());
        LogHelper logHelperToTest = $LogHelper().withLogFile(testLog).build();

        Date resultTime = logHelperToTest.getTimeOfLastServerLogEntry();

        Calendar resultCal = Calendar.getInstance();
        resultCal.setTime(resultTime);
        assertThat(resultCal.get(Calendar.HOUR_OF_DAY)).isEqualTo(20);
        assertThat(resultCal.get(Calendar.MINUTE)).isEqualTo(28);
        assertThat(resultCal.get(Calendar.SECOND)).isEqualTo(29);
    }

    @Test
    public void printCPULoad() {
        Logger mockLog = Mockito.mock(Logger.class);
        LogHelper logHelperToTest = $LogHelper().withLogger(mockLog).build();

        logHelperToTest.printCPULoad(123.4);

        verify(mockLog).info("CPU: 123.4");
    }

    @Test
    public void doFakeLogEntry() {
        Logger mockLog = Mockito.mock(Logger.class);
        LogHelper logHelperToTest = $LogHelper().withLogger(mockLog).build();

        logHelperToTest.doFakeLogEntry();

        verify(mockLog).info(".");
    }

    @Test
    public void tailCorrect() {
        File testLog = new File(TEST_LOG_URL.getPath());
        LogHelper logHelperToTest = $LogHelper().build();

        String oneLine = logHelperToTest.tail(testLog, 1);

        assertThat(oneLine.trim()).isEqualTo("[20:28:29] [Server thread/INFO] [FML/]: Unloading dimension 11");

        File macTestLog = new File(TEST_LOG_URL_MAC.getPath());

        oneLine = logHelperToTest.tail(macTestLog, 1);

        assertThat(oneLine.trim()).isEqualTo("[20:28:29] [Server thread/INFO] [FML/]: Unloading dimension 11");
    }

    @Test
    public void tailNoLogFile() {
        File testLog = new File("some/Crazy/Path");
        Logger mockLogger = Mockito.mock(Logger.class);
        LogHelper logHelperToTest = $LogHelper().withLogger(mockLogger).build();

        String oneLine = logHelperToTest.tail(testLog, 1);

        assertThat(oneLine).isNullOrEmpty();
        verify(mockLogger).warning(anyString());
    }
}