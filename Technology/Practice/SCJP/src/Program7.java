

class Util {
    public enum State{ACTIVE, DELETED, INACTIVE}
}

public class Program7 {     
    public static void main(String args[]) {
        //some code goes here  
    	Util.State state = Util.State.INACTIVE;
    	
    	Object myObj = new String[]{"one", "two", "three"};{
    		        for (String s : (String[])myObj) System.out.print(s + ".");
    		      }
    }
    
    
    /*A) State state = State.INACTIVE;
    B) State state = INACTIVE;
    C) Util.State state = Util.State.INACTIVE;
    D) State state = Util.INACTIVE;*/
}
