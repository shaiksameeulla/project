package basic.regexp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExpDemo4 {

	public static void main(String[] args) {
		Pattern p = Pattern.compile("a+");
		Matcher m= p.matcher("abaaba*7839  36_@#$%^&*(");
		
		while(m.find()){
			System.out.println("index["+m.start() +"]<-----> group "+m.group());
		}

	}

}
