import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 15; i++) {
            executor.execute(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + ": Hi");
                    Thread.sleep(1000);
                    System.out.println(Thread.currentThread().getName() + ": Bye");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }/
            });
        }
        executor.shutdown();
    }
}