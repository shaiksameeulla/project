/*
 * @author soagarwa
 */
package src.com.capgemini.lbs.mdbutil;


// TODO: Auto-generated Javadoc
/**
 * The Class ExceptionUtil.
 */
public abstract class ExceptionUtil {
	
	/**
	 * this method is responsible for converting the exception stack tract to string.
	 *
	 * @param aThrowable the a throwable
	 * @return the exception stack trace
	 */
	public static String getExceptionStackTrace(Throwable aThrowable) {
	    //add the class name and any message passed to constructor
	    final StringBuilder result = new StringBuilder( "Exception stack trace: " );
	    result.append(aThrowable.toString());
	    String newLine = System.getProperty("line.separator");
	    result.append(newLine);

	    //add each element of the stack trace
	    for (StackTraceElement element : aThrowable.getStackTrace() ){
	      result.append( element );
	      result.append( newLine );
	    }
	    return result.toString();
	  }

}
