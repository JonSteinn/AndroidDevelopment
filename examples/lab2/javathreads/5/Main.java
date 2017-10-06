import java.util.concurrent.Semaphore;

public class Main {
    public static void main(String[] args) {
        Semaphore mutex1 = new Semaphore(1);
        Semaphore mutex2 = new Semaphore(1);
        Thread t1 = new Thread(() -> {
            try {
                mutex1.acquire();
                mutex2.acquire();
                mutex1.release();
                mutex2.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread t2 = new Thread(() -> {
            try {
                mutex2.acquire();
                mutex1.acquire();
                mutex2.release();
                mutex1.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t1.start();
        t2.start();
    }
}