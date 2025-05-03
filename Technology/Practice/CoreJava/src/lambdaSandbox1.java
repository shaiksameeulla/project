import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Predicate;

/**
 * 
 */

/**
 * @author mohammes
 *
 */


public class lambdaSandbox1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Collection<Integer> myInts =  Arrays.asList(0,1,2,3,4,5,6,7,8,9);
		Collection<Integer> onlyOdds = filter(n -> n % 2 != 0, myInts);
		System.out.println(onlyOdds);

	}

	public static <T> Collection<T> filter(Predicate<T> predicate,
			Collection<T> items) {
		Collection<T> result = new ArrayList<T>();
		for(T item: items) {
			if(predicate.test(item)) {
				result.add(item);
			}
		}
		return result;
	}

}
