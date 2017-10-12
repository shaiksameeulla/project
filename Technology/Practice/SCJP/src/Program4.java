
public class Program4 {
    private Integer value = 1;
    
    public Integer getValue() {
        return value;
    }
    
    public void changeVal(Integer value) {
        value = new Integer(3);
    }

    public static void main(String args[]) {
        Integer a = new Integer(2);
        Program4 c = new Program4();
        c.changeVal(a);
        System.out.print(a);
    }
}

