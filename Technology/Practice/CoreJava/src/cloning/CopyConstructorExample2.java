package cloning;


public class CopyConstructorExample2 {

    public static void main(String[] args) {
        Mammal mammal = new Mammal("Human");
        Mammal clonedMammal = new Mammal(mammal);
        
        Mammal mammalHuman = new Human("Human", "Mahesh");
        Mammal clonedMammalHuman = new Human(mammalHuman);        

        System.out.println("Object " + mammal + " and copied object " + clonedMammal + " are == : " + (mammal == clonedMammal));
        System.out.println("Object " + mammal + " and copied object " + clonedMammal + " are equal : " + (mammal.equals(clonedMammal)) + "\n");
    }

}

