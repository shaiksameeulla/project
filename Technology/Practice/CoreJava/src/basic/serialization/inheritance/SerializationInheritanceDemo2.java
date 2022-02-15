package basic.serialization.inheritance;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializationInheritanceDemo2 {
	
	public static void main(String s[]) throws IOException, ClassNotFoundException{
		Child e= new Child();
		
		e.setBaseObj("Base-setter");
		e.setSbaseObj("super setter");
		e.setChildObj("child");
		serialize(e, "child.txt");
		System.out.println("Serialization completed---------------- ");
		Child e2= (Child)deserialize("child.txt");
		System.out.println(e2.toString());
		System.out.println(e2.getBaseObj());
		System.out.println(e2.getSbaseObj());
		
		
	}
	
	// deserialize to Object from given file
		public static Object deserialize(String fileName) throws IOException,
				ClassNotFoundException {
			FileInputStream fis = new FileInputStream(fileName);
			ObjectInputStream ois = new ObjectInputStream(fis);
			Object obj = ois.readObject();
			ois.close();
			return obj;
		}

		// serialize the given object and save it to file
		public static void serialize(Object obj, String fileName)
				throws IOException {
			FileOutputStream fos = new FileOutputStream(fileName);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(obj);

			fos.close();
		}

}
