package basic.serialization.customization;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializationInheritanceDemo2 {
	
	public static void main(String s[]) throws IOException, ClassNotFoundException{
		SampleSerialization e= new SampleSerialization();
		
		
		serialize(e, "SampleSerialization.txt");
		System.out.println("Serialization completed ");
		SampleSerialization e2= (SampleSerialization)deserialize("SampleSerialization.txt");
		System.out.println(e2.toString());
		System.out.println(e2.getVar1());
		System.out.println(e2.getVar2());
		System.out.println(e2.getSecName());
		System.out.println(e2.getSecNumber());
		
		
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
