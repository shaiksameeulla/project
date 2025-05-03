import java.util.LinkedList;
import java.util.List;

class Empty {   
}

class Extended extends Empty {  
}

public class Program2 {    
        public static void doStuff1(List<Empty> list) {
                // some code
        }
        public static void doStuff2(List list) {        
                // some code
        }
        public static void doStuff3(List<? extends Empty> list) {
                // some code            
        }
        
        public static void main(String args[]) {
                List<Empty> list1 = new LinkedList<Empty>();
                List<Extended> list2 = new LinkedList<Extended>();
                
                // more code here
                doStuff1(list1);
                doStuff2(list1);
                doStuff3(list1);
                
               // doStuff1(list2); error
                doStuff2(list2);
                doStuff3(list2);
                
                
        }
        
        /*A) doStuff1(list1);
        B) doStuff2(list2);
        C) doStuff2(list1);
        D) doStuff3(list1);
        E) doStuff3(list2);
        F) doStuff1(list2);*/
}

