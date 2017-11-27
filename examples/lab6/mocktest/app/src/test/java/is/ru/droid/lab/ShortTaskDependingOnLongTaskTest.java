package is.ru.droid.lab;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Jonni on 11/27/2017.
 */
public class ShortTaskDependingOnLongTaskTest {
    @Test
    public void testShortTask() {
        LongTask lTask = mock(LongTask.class);
        when(lTask.longTask(3)).thenReturn(5);
        ShortTaskDependingOnLongTask sTask = new ShortTaskDependingOnLongTask(lTask);
        assertEquals(125, sTask.shortTaskDependingOnLongTask(3));
    }
}