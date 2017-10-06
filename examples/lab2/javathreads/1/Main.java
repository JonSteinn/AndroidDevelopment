public class Main {
    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> System.out.print("A"));
        Thread thread2 = new Thread(() -> System.out.print("1"));
        thread1.start();
        thread2.start();
        System.out.print("X");
    }
}