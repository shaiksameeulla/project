package basic.inheritance;

class Hotel {
    public void book() throws Exception {
        throw new Exception();
    }
}

public class SuperHotel extends Hotel  {
    public void book() {
        System.out.print("booked");
    }
    
    public static void main(String args[]) throws Exception {
        Hotel h = new SuperHotel();
        h.book();
    }   
}