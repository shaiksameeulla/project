public class Program11 implements Runnable {
    
    public void run() {
        System.out.print("go");
    }
    
    public static void main(String arg[]) {
        Thread t = new Thread(new Program11());
        t.run();
        t.run();
        t.start();
    }
}
