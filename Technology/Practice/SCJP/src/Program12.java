public class Program12  {
    
    private static void book() {
        System.out.print("book");
    }
    
    public static void main(String[] args) {
        try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        book();
    }
}
