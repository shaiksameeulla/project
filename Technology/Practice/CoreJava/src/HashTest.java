import java.util.HashSet;

public class HashTest {
	
	static{System.out.println("static1");}
	static{System.out.println("static2");}
	{System.out.println("instance block");}
	
	HashTest(){
		System.out.println("constructor");
	}
    
    private String str;
    
    public HashTest(String str) {
        this.str = str;
    }
    
    @Override
    public String toString() {      
        return str;
    }
    
    @Override
    public int hashCode() {             
        return this.str.hashCode();
    }
    
    @Override
    public boolean equals(Object obj) { 
        if (obj instanceof HashTest) {
            HashTest ht = (HashTest) obj;
            return this.str.equals(ht.str);
        }
        return false;
    }
    private static void book() {
        System.out.print("book");
    }
    public static void main(String args[]) {
        HashTest h1 = new HashTest();
        h1.str="1";
        HashTest h2 = new HashTest();
        h2.str="1";
        String s1 = new String("2");
        String s2 = new String("2");
        book();
        System.getProperty("myprop");
        HashSet<Object> hs = new HashSet<Object>();
        hs.add(h1);
        hs.add(h2);
        hs.add(s1);
        hs.add(s2);
        
        System.out.print(hs.size());
    }
}