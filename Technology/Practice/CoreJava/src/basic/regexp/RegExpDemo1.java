package basic.regexp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExpDemo1 {
	

	public static void main(String[] args) {
		Pattern p = Pattern.compile("[^abc]");
		Matcher m= p.matcher("ababa*783936_@#$%^&*(");
		
		while(m.find()){
			System.out.println("index["+m.start() +"]<-----> group"+m.group());
		}

	}

}
