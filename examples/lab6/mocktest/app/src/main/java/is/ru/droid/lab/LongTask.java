package is.ru.droid.lab;

public class LongTask {

    public int longTask(int x) {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return x + 2;
    }
}
