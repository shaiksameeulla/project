package general.comparator;


import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CustomsComparator	 {

	public static void main(String[] args) {

		  List<String> cast = Arrays.asList("ten","nine","Eight","ONE","THREE");
		  System.out.println(cast);

		  Collections.sort(cast, new StarTrekSorter());

		  for (String trek : cast) {
		    System.out.println(trek);
		  }  
		}

}

 class StarTrekSorter implements Comparator<String> {

	  private static final List<String> ORDERED_ENTRIES = Arrays.asList("ONE","TWO","THREE");

	  @Override
	  public int compare(String o1, String o2) {
	    if (ORDERED_ENTRIES.contains(o1) && ORDERED_ENTRIES.contains(o2)) {
	      // Both objects are in our ordered list. Compare them by
	      // their position in the list
	      return ORDERED_ENTRIES.indexOf(o1) - ORDERED_ENTRIES.indexOf(o2);
	    }

	    if (ORDERED_ENTRIES.contains(o1)) {
	      // o1 is in the ordered list, but o2 isn't. o1 is smaller (i.e. first)
	      return -1;
	    }

	    if (ORDERED_ENTRIES.contains(o2)) {
	      // o2 is in the ordered list, but o1 isn't. o2 is smaller (i.e. first)
	      return 1;
	    }

	    return o1.compareTo(o2);
	  }
	}
