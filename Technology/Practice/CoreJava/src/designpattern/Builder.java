package designpattern;

public class Builder {
	private int a;
	Builder(BuilderHelper builkder){
		this.a =builkder.var;
	}


	public static class BuilderHelper {
		public int getVar() {
			return var;
		}

		public void setVar(int var) {
			this.var = var;
		}

		private int var;
		
		Builder build(){
		return new Builder(this);
		}

	}

}
