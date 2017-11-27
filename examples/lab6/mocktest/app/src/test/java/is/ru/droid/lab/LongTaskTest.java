package is.ru.droid.lab;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LongTaskTest {

    @Test
    public void testLongTask() {
        LongTask longTask = new LongTask();
        assertEquals(longTask.longTask(3), 5);
    }
}