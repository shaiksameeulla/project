
public class Program6 {
    
    public static void printB(String str) {
         System.out.print(Boolean.valueOf(str) ? "true" : "false"); 
    }
    
    public static void main(String args[]) {
        printB("tRuE");
        printB("false");
    }
}
