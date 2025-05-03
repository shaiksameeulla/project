package cloning;

public  class Mammal {

    protected String type;

    public Mammal(String type) {
        this.type = type;
    }

    public Mammal(Mammal original) {
        this.type = original.type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Mammal cloneObject() {
        return new Mammal(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Mammal mammal = (Mammal) o;

        if (!type.equals(mammal.type)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return type.hashCode();
    }

    @Override
    public String toString() {
        return "Mammal{" + "type='" + type + "'}";
    }
}
