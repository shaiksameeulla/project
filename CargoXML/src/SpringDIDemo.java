import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 
 */

/**
 * @author mohammes
 *
 */
public class SpringDIDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				"springDi.xml");

		// close the context
		context.close();

	}

}
