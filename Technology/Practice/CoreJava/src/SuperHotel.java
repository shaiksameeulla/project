import java.util.Locale;

/**
 * 
 */

/**
 * @author mohammes
 *
 */

class Hotel {
    public void book() throws Exception {
        throw new Exception();
    }
}

public class SuperHotel extends Hotel  {
    public void book() {
        System.out.print("booked");
    }
    
    public static void main(String args[]) {
        Hotel h = new SuperHotel();
        Locale l = Locale.getDefault();
        System.out.println(l.getDisplayCountry());
    }   
}
