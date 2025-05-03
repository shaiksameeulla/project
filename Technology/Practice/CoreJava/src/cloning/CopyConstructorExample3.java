package cloning;


public class CopyConstructorExample3 {

    public static void main(String[] args) {
        Mammal mammal = new Mammal("Human");
        Human human = new Human("Human", "Naresh");

        Mammal mammalHuman = new Human("Human", "Mahesh");

        Mammal clonedMammal = new Mammal(mammal);
        //Mammal clonedMammal = mammal.cloneObject();

        Human clonedHuman = new Human(human);
        //Human clonedHuman = human.cloneObject();

        //Mammal clonedMammalHuman = new Human(mammalHuman); // compilation error
        //Mammal clonedMammalHuman = new Mammal(mammalHuman);
        Mammal clonedMammalHuman = mammalHuman.cloneObject();

        System.out.println("Object " + mammal + " and copied object " + clonedMammal + " are == : " + (mammal == clonedMammal));
        System.out.println("Object " + mammal + " and copied object " + clonedMammal + " are equal : " + (mammal.equals(clonedMammal)) + "\n");


        System.out.println("Object " + human + " and copied object " + clonedHuman + " are == : " + (human == clonedHuman));
        System.out.println("Object " + human + " and copied object " + clonedHuman + " are equal : " + (human.equals(clonedHuman)) + "\n");

        System.out.println("Object " + mammalHuman + " and copied object " + clonedMammalHuman + " are == : " + (mammalHuman == clonedMammalHuman));
        System.out.println("Object " + mammalHuman + " and copied object " + clonedMammalHuman + " are equal : " + (mammalHuman.equals(clonedMammalHuman)) + "\n");

    }

}

