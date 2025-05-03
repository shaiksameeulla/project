import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * 
 */

/**
 * @author mohammes
 *
 */
public class Program1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, Locale.US);
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, 2009);
		c.set(Calendar.MONTH, 6);
		c.set(Calendar.DAY_OF_MONTH, 1);
		String formattedDate = df.format(c.getTime());
		System.out.println(formattedDate);
	}

}
