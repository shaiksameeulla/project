package cloning;

public class Human extends Mammal {

    protected String name;

    public Human(String type, String name) {
        super(type);
        this.name = name;
    }

    public Human(Human original) {
        super(original.type);
        this.name = original.name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Human cloneObject() {
        return new Human(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Human human = (Human) o;

        if (!type.equals(human.type)) return false;
        if (!name.equals(human.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Human{" + "type='" + type + "', name='" + name + "'}";
    }
}