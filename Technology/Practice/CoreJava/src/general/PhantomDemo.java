package general;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
class A{}
public class PhantomDemo {

	public static void main(String[] args) {A a = new A();      //Strong Reference
	System.out.println( "A just created"+a);
    //Creating ReferenceQueue

    ReferenceQueue<A> refQueue = new ReferenceQueue<A>();

    //Creating Phantom Reference to A-type object to which 'a' is also pointing

    PhantomReference<A> phantomA = new PhantomReference<A>(a, refQueue);
    System.out.println( "A just pointed to phantom"+a);
    

    a = null;    //Now, A-type object to which 'a' is pointing earlier is available for garbage collection. But, this object is kept in 'refQueue' before removing it from the memory.
    System.out.println( "A just after making  null "+a);
    a = phantomA.get();
    System.out.println( "A just after making  null , getting from phantom "+a);
    
	}

}
