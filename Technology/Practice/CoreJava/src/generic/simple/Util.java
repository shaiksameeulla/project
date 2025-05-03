package generic.simple;

public class Util {
    public static <K, V> boolean compare(MyGeneric2<K, V> p1, MyGeneric2<K, V> p2) {
        return p1.getKey().equals(p2.getKey()) &&
               p1.getValue().equals(p2.getValue());
    }
    
    public  <K, V> boolean compare1(MyGeneric2<K, V> p1, MyGeneric2<K, V> p2) {
        return p1.getKey().equals(p2.getKey()) &&
               p1.getValue().equals(p2.getValue());
    }
    
    public  <T> boolean compare1(MyGeneric<T> p1) {
        return p1.variable == p1.variable;
    }
}
