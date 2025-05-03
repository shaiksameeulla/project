import java.util.PriorityQueue;
import java.util.Queue;

public class Program10 {
	
static int x[];
    
    static {
        x[0] = 1;
    }
    public static void main(String args[]) {
        Queue<String> q = new PriorityQueue<String>();
        q.add("3");
        q.add("1");
        q.add("2");
        q.add("20");
        q.add("-1");
        System.out.println(q);
        System.out.print(q.poll() + " ");
        System.out.print(q.peek() + " ");
        System.out.print(q.peek());
        System.out.println(q);
    }
}
