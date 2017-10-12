

public class Program8<X extends Object> {
    
    private X x;
    
    public Program8(X x) {
        this.x = x;
    }
    
    private double getDouble() {
        return ((Double) x).doubleValue();
    }
    
    public static void main(String args[]) {
    	Program8<String> a = new Program8<String>(new String("1"));
        System.out.print(a.getDouble());
    }
}
