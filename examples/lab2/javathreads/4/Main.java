import java.util.concurrent.Semaphore;

public class Main {
    private static int counter = 0, a = 0, b = 0;
    public static void main(String[] args) {
        Semaphore mutex = new Semaphore(1);
        try {
            Thread[] threads = new Thread[1000];
            for (int i = 0; i < threads.length; i++) {
                threads[i] = new Thread(() -> {
                    try {
                        mutex.acquire();
                        if (++a == ++b) counter++;
                        mutex.release();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
                threads[i].start();
            }
            for (Thread t : threads) t.join();
            System.out.println(counter);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}