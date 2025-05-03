package basic.serialization.customization;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class SampleSerialization implements Serializable {

	public int getSecNumber() {
		return secNumber;
	}

	public void setSecNumber(int secNumber) {
		this.secNumber = secNumber;
	}

	private String var1 = "var1";

	private String var2 = "var2";
	private transient int secNumber = 88;
	private transient String secName = "sami";

	public String getVar1() {
		return var1;
	}

	public void setVar1(String var1) {
		this.var1 = var1;
	}

	public String getVar2() {
		return var2;
	}

	public void setVar2(String var2) {
		this.var2 = var2;
	}

	private void writeObject(ObjectOutputStream out) throws IOException {
		setVar1("sami");
		out.defaultWriteObject();
		out.writeInt(secNumber);
		out.writeObject(secName);
	}

	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		secNumber = in.readInt();
		secName = (String) in.readObject();
	}

	public String getSecName() {
		return secName;
	}

	public void setSecName(String secName) {
		this.secName = secName;
	}

}
