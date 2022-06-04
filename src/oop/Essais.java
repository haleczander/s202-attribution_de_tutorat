package oop;

public class Essais {
    public static void main(String[] args) {

        Teacher jc = new Teacher("Jean Carle", "R102");
        jc.addResource(Resource.R105);
        System.out.println(jc);
        System.out.println(Resource.R105);
        Resource.setShortName(true);
        System.out.println(Resource.R105);
    }
}
