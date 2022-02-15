package basic.regexp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExpDemo2 {

	public static void main(String[] args) {
		Pattern p = Pattern.compile(".");
		Matcher m= p.matcher("ababa*7839  36_@#$%^&*(");
		
		while(m.find()){
			System.out.println("index["+m.start() +"]<-----> group"+m.group());
		}

	}

}
