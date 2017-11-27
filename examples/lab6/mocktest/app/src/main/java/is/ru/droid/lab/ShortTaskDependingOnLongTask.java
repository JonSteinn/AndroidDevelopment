package is.ru.droid.lab;

public class ShortTaskDependingOnLongTask {
    private LongTask task;
    public ShortTaskDependingOnLongTask(LongTask task) {
        this.task = task;
    }
    public int shortTaskDependingOnLongTask(int x) {
        int tmp = task.longTask(x);
        return tmp * tmp * tmp;
    }
}
